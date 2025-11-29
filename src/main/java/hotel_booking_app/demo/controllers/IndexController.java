package hotel_booking_app.demo.controllers;


import hotel_booking_app.demo.entities.Hotel;
import hotel_booking_app.demo.services.HotelService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class IndexController {

    private final HotelService hotelService;

    public IndexController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @GetMapping("/")
    public String home(@RequestParam(value = "location", required = false) String location, Model model) {
        // Ако потребителят е търсил нещо (location != null), връщаме филтрирани хотели.
        // Ако не е търсил, връщаме всички (или само топ 3-4 за красота).

        List<Hotel> hotels = hotelService.searchHotels(location);

        model.addAttribute("hotels", hotels);
        model.addAttribute("searchLocation", location); // За да запазим какво е писал в полето

        return "home";
    }
}
