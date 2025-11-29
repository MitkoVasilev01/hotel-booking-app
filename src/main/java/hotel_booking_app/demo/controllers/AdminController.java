package hotel_booking_app.demo.controllers;

import hotel_booking_app.demo.entities.Hotel;
import hotel_booking_app.demo.enums.HotelCategory;
import hotel_booking_app.demo.services.HotelService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Controller
@RequestMapping("/admin")
public class AdminController {

    private final HotelService hotelService;

    public AdminController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    // --- ТОЗИ МЕТОД ТИ ЛИПСВА (GET) ---
    @GetMapping("/hotels/add")
    public String addHotelForm(Model model) {
        model.addAttribute("hotel", new Hotel());
        model.addAttribute("categories", HotelCategory.values());
        return "admin/add-hotel"; // Търси файла в templates/admin/add-hotel.html
    }

    // --- ТОЗИ МЕТОД ОБРАБОТВА ФОРМАТА (POST) ---
    @PostMapping("/hotels/add")
    public String addHotel(Hotel hotel, @RequestParam("amenitiesText") String amenitiesText, // Взимаме текста отделно
                           @RequestParam("galleryText") String galleryText, Model model) {

        // 1. Подготовка на галерията (чистим празните места)
        Set<String> cleanGalleryLinks = new HashSet<>();
        if (!galleryText.isEmpty()) {
            String[] splitImages = galleryText.split(",");
            for (String img : splitImages) {
                if (!img.trim().isEmpty()) { // Проверка за празни линкове
                    cleanGalleryLinks.add(img.trim());
                }
            }
        }

        // 2. ВАЛИДАЦИЯ: Искаме поне 4 снимки в галерията (+1 основна = 5)
        if (cleanGalleryLinks.size() < 4) {
            model.addAttribute("errorMessage", "Трябва да добавите поне 4 снимки в галерията (общо 5 с основната)!");
            model.addAttribute("hotel", hotel); // Връщаме въведените данни, за да не ги губи
            model.addAttribute("categories", HotelCategory.values());
            return "admin/add-hotel"; // Връщаме потребителя във формата
        }

        // 3. Ако всичко е наред, добавяме данните
        hotel.setGalleryImages(cleanGalleryLinks);
        // 1. Обработка на екстрите (разделяме по запетая)
        if (!amenitiesText.isEmpty()) {
            String[] splitAmenities = amenitiesText.split(",");
            for (String item : splitAmenities) {
                hotel.getAmenities().add(item.trim()); // trim() маха разстоянията
            }
        }

        // 2. Обработка на галерията (разделяме по запетая)
        if (!galleryText.isEmpty()) {
            String[] splitImages = galleryText.split(",");
            for (String img : splitImages) {
                hotel.getGalleryImages().add(img.trim());
            }
        }
        hotelService.createHotel(hotel);
        return "redirect:/";
    }
}