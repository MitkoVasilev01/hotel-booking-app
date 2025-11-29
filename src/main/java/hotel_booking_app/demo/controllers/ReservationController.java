package hotel_booking_app.demo.controllers; // <--- Увери се, че това съвпада с папката ти!

import hotel_booking_app.demo.entities.Hotel;
import hotel_booking_app.demo.entities.Reservation;
import hotel_booking_app.demo.entities.User;
import hotel_booking_app.demo.enums.BookingStatus;
import hotel_booking_app.demo.models.binding.ReservationBindingModel;
import hotel_booking_app.demo.services.HotelService;
import hotel_booking_app.demo.services.ReservationService;
import hotel_booking_app.demo.services.UserService;
import org.springframework.stereotype.Controller; // <--- ВАЖЕН ИМПОРТ
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.UUID;

@Controller // <--- БЕЗ ТОВА SPRING НЕ ВИЖДА КЛАСА
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

        System.out.println("✅ КОНТРОЛЕРЪТ Е НАМЕРЕН! Започва резервация...");

        // 1. Проверка на user
        User user = userService.findByName(principal.getName())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // 2. Проверка на хотел
        Hotel hotel = hotelService.getHotelById(model.getHotelId())
                .orElseThrow(() -> new IllegalArgumentException("Hotel not found"));

        // 3. Създаване на обект
        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setHotel(hotel);
        reservation.setStartDate(model.getStartDate());
        reservation.setEndDate(model.getEndDate());
        reservation.setStatus(BookingStatus.PENDING);

        // 4. Запис в базата
        reservationService.createReservation(reservation);

        System.out.println("✅ РЕЗЕРВАЦИЯТА Е ЗАПИСАНА В БАЗАТА!");

        // Тъй като още нямаме профил страница, временно те пращам в началната
        return "redirect:/users/profile";
    }
    @PostMapping("/pay/{id}")
    public String payReservation(@PathVariable UUID id) {

        reservationService.payForReservation(id);

        return "redirect:/users/profile";
    }
}