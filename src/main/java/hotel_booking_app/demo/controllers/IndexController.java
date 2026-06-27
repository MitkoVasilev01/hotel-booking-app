package hotel_booking_app.demo.controllers;

import hotel_booking_app.demo.entities.Hotel;
import hotel_booking_app.demo.services.HotelService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {

    private final HotelService hotelService;

    public IndexController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @GetMapping("/")
    public String home(@RequestParam(value = "location", required = false) String location,
                       @RequestParam(value = "page", defaultValue = "0") int page,
                       @RequestParam(value = "size", defaultValue = "3") int size,
                       Model model) {

        Page<Hotel> hotelPage = hotelService.getHotelsPaged(location, page, size);

        model.addAttribute("hotelPage", hotelPage);
        model.addAttribute("hotels", hotelPage.getContent());
        model.addAttribute("searchLocation", location);

        return "home";
    }
}