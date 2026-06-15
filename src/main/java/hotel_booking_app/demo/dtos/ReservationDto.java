package hotel_booking_app.demo.dtos;

import hotel_booking_app.demo.entities.Hotel;
import hotel_booking_app.demo.entities.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class ReservationDto {

    private User user;
    private Hotel hotel;
    private UUID hotelId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
        private LocalDate startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
        private LocalDate endDate;

    private Double pricePerNight;

    private Double totalPrice;


    }

