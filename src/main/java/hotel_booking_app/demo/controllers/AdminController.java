package hotel_booking_app.demo.controllers;

import hotel_booking_app.demo.entities.Hotel;
import hotel_booking_app.demo.enums.HotelCategory;
import hotel_booking_app.demo.services.HotelService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.util.StringUtils;
import hotel_booking_app.demo.util.FileUploadUtil;
import java.io.IOException;
import java.util.UUID;

import java.util.*;


@Controller
@RequestMapping("/admin")
public class AdminController {

    private final HotelService hotelService;

    public AdminController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @GetMapping("/hotels/add")
    public String addHotelForm(Model model) {
        model.addAttribute("hotel", new Hotel());
        model.addAttribute("categories", HotelCategory.values());
        return "admin/add-hotel";
    }

    @PostMapping("/hotels/add")
    public String addHotel(Hotel hotel,
                           @RequestParam("amenitiesText") String amenitiesText,
                           @RequestParam("mainImageFile") MultipartFile mainImageFile,
                           @RequestParam("galleryFiles") MultipartFile[] galleryFiles,
                           Model model) throws IOException {


        if (!mainImageFile.isEmpty()) {

            String fileName = UUID.randomUUID().toString() + "_" + StringUtils.cleanPath(mainImageFile.getOriginalFilename());


            String uploadDir = "hotel-photos";
            FileUploadUtil.saveFile(uploadDir, fileName, mainImageFile);


            hotel.setImageUrl("/photos/" + fileName);
        }


        Set<String> galleryLinks = new HashSet<>();

        for (MultipartFile file : galleryFiles) {
            if (!file.isEmpty()) {
                String fileName = UUID.randomUUID().toString() + "_" + StringUtils.cleanPath(file.getOriginalFilename());
                String uploadDir = "hotel-photos";

                FileUploadUtil.saveFile(uploadDir, fileName, file);

                galleryLinks.add("/photos/" + fileName);
            }
        }

        if (galleryLinks.size() < 4) {
            model.addAttribute("errorMessage", "Трябва да качите поне 4 снимки в галерията!");
            model.addAttribute("hotel", hotel);
            model.addAttribute("categories", HotelCategory.values());
            return "admin/add-hotel";
        }

        hotel.setGalleryImages(galleryLinks);


        if (!amenitiesText.isEmpty()) {
            String[] splitAmenities = amenitiesText.split(",");
            for (String item : splitAmenities) {
                if (!item.trim().isEmpty()) hotel.getAmenities().add(item.trim());
            }
        }

        hotelService.createHotel(hotel);

        return "redirect:/";
    }
    @GetMapping("/hotels/edit/{id}")
    public String editHotelForm(@PathVariable UUID id, Model model) {
        Hotel hotel = hotelService.getHotelById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid hotel Id:" + id));

        model.addAttribute("hotel", hotel);
        model.addAttribute("categories", HotelCategory.values());

        String amenitiesString = String.join(", ", hotel.getAmenities());
        String galleryString = String.join(", ", hotel.getGalleryImages());

        model.addAttribute("amenitiesText", amenitiesString);
        model.addAttribute("galleryText", galleryString);

        return "admin/edit-hotel";
    }

    @PostMapping("/hotels/edit/{id}")
    public String editHotel(@PathVariable UUID id,
                            Hotel hotel,
                            @RequestParam("amenitiesText") String amenitiesText,
                            @RequestParam("galleryText") String galleryText) {

        hotel.setId(id);

        Set<String> cleanGalleryLinks = new HashSet<>();
        if (!galleryText.isEmpty()) {
            String[] splitImages = galleryText.split(",");
            for (String img : splitImages) {
                if (!img.trim().isEmpty()) cleanGalleryLinks.add(img.trim());
            }
        }
        hotel.setGalleryImages(cleanGalleryLinks);

        Set<String> cleanAmenities = new HashSet<>();
        if (!amenitiesText.isEmpty()) {
            String[] splitAmenities = amenitiesText.split(",");
            for (String item : splitAmenities) {
                if (!item.trim().isEmpty()) cleanAmenities.add(item.trim());
            }
        }
        hotel.setAmenities(cleanAmenities);

        hotelService.updateHotel(hotel);

        return "redirect:/hotels/details/" + id;
    }
}