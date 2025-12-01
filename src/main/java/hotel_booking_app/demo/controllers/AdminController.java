package hotel_booking_app.demo.controllers;

import hotel_booking_app.demo.entities.Hotel;
import hotel_booking_app.demo.enums.HotelCategory;
import hotel_booking_app.demo.services.HotelService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        return "admin/add-hotel"; // Търси файла в templates/admin/add-hotel.html
    }

    @PostMapping("/hotels/add")
    public String addHotel(Hotel hotel, @RequestParam("amenitiesText") String amenitiesText,
                           @RequestParam("galleryText") String galleryText, Model model) {

        Set<String> cleanGalleryLinks = new HashSet<>();
        if (!galleryText.isEmpty()) {
            String[] splitImages = galleryText.split(",");
            for (String img : splitImages) {
                if (!img.trim().isEmpty()) {
                    cleanGalleryLinks.add(img.trim());
                }
            }
        }

        if (cleanGalleryLinks.size() < 4) {
            model.addAttribute("errorMessage", "Трябва да добавите поне 4 снимки в галерията (общо 5 с основната)!");
            model.addAttribute("hotel", hotel);
            model.addAttribute("categories", HotelCategory.values());
            return "admin/add-hotel";
        }

        hotel.setGalleryImages(cleanGalleryLinks);
        if (!amenitiesText.isEmpty()) {
            String[] splitAmenities = amenitiesText.split(",");
            for (String item : splitAmenities) {
                hotel.getAmenities().add(item.trim());
            }
        }

        if (!galleryText.isEmpty()) {
            String[] splitImages = galleryText.split(",");
            for (String img : splitImages) {
                hotel.getGalleryImages().add(img.trim());
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