package hotel_booking_app.demo.controllers;

import hotel_booking_app.demo.entities.Hotel;
import hotel_booking_app.demo.services.HotelService;
import hotel_booking_app.demo.services.ReviewService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/hotels")
public class HotelController {

    private final HotelService hotelService;
    private final ReviewService reviewService;

    public HotelController(HotelService hotelService, ReviewService reviewService) {
        this.hotelService = hotelService;
        this.reviewService = reviewService;
    }

    @GetMapping("/all")
    public String getAllHotels(Model model) {
        List<Hotel> hotels = hotelService.getAllHotels();
        model.addAttribute("hotels", hotels);
        return "home";
    }

    @GetMapping("/details/{id}")
    public String getHotelDetails(@PathVariable UUID id, Model model) {
        Hotel hotel = hotelService.getHotelById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid hotel Id:" + id));
        model.addAttribute("hotel", hotel);
        return "hotel-details";
    }

    @PostMapping("/details/{hotelId}/add-review")
    public String addReview(@PathVariable UUID hotelId,
                            @RequestParam("rating") int rating,
                            @RequestParam("comment") String comment,
                            Principal principal) {

        reviewService.createReview(hotelId, principal.getName(), rating, comment);

        return "redirect:/hotels/details/" + hotelId;
    }
}

