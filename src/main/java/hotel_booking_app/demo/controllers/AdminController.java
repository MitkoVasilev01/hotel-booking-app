package hotel_booking_app.demo.controllers;

import hotel_booking_app.demo.dtos.HotelFormDto;
import hotel_booking_app.demo.enums.HotelCategory;
import hotel_booking_app.demo.services.HotelService;
import hotel_booking_app.demo.util.FileUploadUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.UUID;

@Controller
@RequestMapping("/admin")
@Slf4j
public class AdminController {

    private final HotelService hotelService;

    public AdminController(HotelService hotelService) {
        this.hotelService = hotelService;
    }



    @GetMapping("/hotels/add")
    public String addHotelForm(Model model) {
        model.addAttribute("hotelDto", new HotelFormDto());
        model.addAttribute("categories", HotelCategory.values());
        return "admin/add-hotel";
    }

    @PostMapping("/hotels/add")
    public String addHotel(@ModelAttribute("hotelDto") HotelFormDto hotelDto, Model model) {

        if (hotelDto.getGalleryFiles() == null || FileUploadUtil.getActiveFilesCount(hotelDto.getGalleryFiles()) < 4) {
            model.addAttribute("errorMessage", "Трябва да качите поне 4 снимки в галерията!");
            model.addAttribute("categories", HotelCategory.values());
            return "admin/add-hotel";
        }

        try {
            hotelService.addHotel(hotelDto);
        } catch (IOException e) {
            model.addAttribute("errorMessage", "Грешка при записването на снимките!");
            model.addAttribute("categories", HotelCategory.values());
            return "admin/add-hotel";
        }

        return "redirect:/";
    }

    @GetMapping("/hotels/edit/{id}")
    public String editHotelForm(@PathVariable UUID id, Model model) {
        HotelFormDto hotelDto = hotelService.getHotelFormDtoById(id);

        model.addAttribute("hotelDto", hotelDto);
        model.addAttribute("categories", HotelCategory.values());
        return "admin/edit-hotel";
    }

    @PostMapping("/hotels/edit/{id}")
    public String editHotel(@PathVariable UUID id, @ModelAttribute("hotelDto") HotelFormDto hotelDto, Model model) {
        try {
            hotelService.updateHotel(id, hotelDto);
        } catch (Exception e) {
            log.error("Грешка при редактиране на хотел с ID: {}", id, e);
            model.addAttribute("errorMessage", "Възникна грешка при редактирането на хотела!");
            model.addAttribute("categories", HotelCategory.values());
            return "admin/edit-hotel";
        }
        return "redirect:/hotels/details/" + id;
    }

    @PostMapping("/hotels/delete/{id}")
    public String deleteHotel(@PathVariable UUID id) {
        hotelService.deleteHotel(id);
        return "redirect:/hotels/all";
    }





}