package hotel_booking_app.demo.controllers;

import hotel_booking_app.demo.entities.Hotel;
import hotel_booking_app.demo.entities.Reservation;
import hotel_booking_app.demo.entities.User;
import hotel_booking_app.demo.enums.BookingStatus;
import hotel_booking_app.demo.models.binding.ReservationBindingModel;
import hotel_booking_app.demo.services.HotelService;
import hotel_booking_app.demo.services.ReservationService;
import hotel_booking_app.demo.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.UUID;

@Controller
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;
    private final UserService userService;
    private final HotelService hotelService;

    public ReservationController(ReservationService reservationService, UserService userService, HotelService hotelService) {
        this.reservationService = reservationService;
        this.userService = userService;
        this.hotelService = hotelService;
    }

    @PostMapping("/create")
    public String createReservation(@ModelAttribute ReservationBindingModel model, Principal principal) {


        User user = userService.findByName(principal.getName())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Hotel hotel = hotelService.getHotelById(model.getHotelId())
                .orElseThrow(() -> new IllegalArgumentException("Hotel not found"));

        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setHotel(hotel);
        reservation.setStartDate(model.getStartDate());
        reservation.setEndDate(model.getEndDate());
        reservation.setStatus(BookingStatus.PENDING);

        long days = java.time.temporal.ChronoUnit.DAYS.between(model.getStartDate(), model.getEndDate());
        if (days < 1) days = 1;

        double total = days * hotel.getPricePerNight();

        reservationService.createReservation(reservation);

        return "redirect:/users/profile";
    }
    @PostMapping("/pay/{id}")
    public String payReservation(@PathVariable UUID id) {

        reservationService.payForReservation(id);

        return "redirect:/users/profile";
    }
}