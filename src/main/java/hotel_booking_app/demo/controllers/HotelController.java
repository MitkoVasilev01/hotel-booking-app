package hotel_booking_app.demo.controllers;

import hotel_booking_app.demo.entities.Hotel;
import hotel_booking_app.demo.entities.Review;
import hotel_booking_app.demo.entities.User;
import hotel_booking_app.demo.repositories.HotelRepository;
import hotel_booking_app.demo.repositories.ReviewRepository;
import hotel_booking_app.demo.services.HotelService;
import hotel_booking_app.demo.services.UserService;
import org.springframework.http.ResponseEntity;
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
    private final UserService userService;         // <--- Ново
    private final ReviewRepository reviewRepository;

    public HotelController(HotelService hotelService, UserService userService, ReviewRepository reviewRepository) {
        this.hotelService = hotelService;
        this.userService = userService;
        this.reviewRepository = reviewRepository;
    }

    @GetMapping("/all")
    public String getAllHotels(Model model) {
        List<Hotel> hotels = hotelService.getAllHotels();
        model.addAttribute("hotels", hotels);
        return "hotels-list";
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

        Hotel hotel = hotelService.getHotelById(hotelId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid hotel Id"));

        String username = principal.getName();
        User user = userService.findByName(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Review review = new Review();
        review.setHotel(hotel);
        review.setUser(user);
        review.setRating(rating);
        review.setComment(comment);

        reviewRepository.save(review);

        return "redirect:/hotels/details/" + hotelId;
    }
}

