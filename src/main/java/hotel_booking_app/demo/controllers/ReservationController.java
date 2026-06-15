package hotel_booking_app.demo.controllers;

import hotel_booking_app.demo.dtos.ReservationDto;
import hotel_booking_app.demo.services.ReservationService;
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

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping("/create")
    public String createReservation(@ModelAttribute ReservationDto reservationDto, Principal principal) {

        reservationService.createReservation(reservationDto, principal.getName());
        return "redirect:/users/profile";
    }
    @PostMapping("/pay/{id}")
    public String payReservation(@PathVariable UUID id) {

      try {
          reservationService.payForReservation(id);
          return "redirect:/users/profile?success=pay";
      } catch (Exception e) {
          e.printStackTrace();
          return "redirect:/users/profile?error=payment_offline";
      }

    }
}