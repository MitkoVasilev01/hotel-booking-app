package hotel_booking_app.demo.entities;

import hotel_booking_app.demo.enums.BookingStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
public class Reservation extends BaseEntity {


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

    private LocalDate startDate;
    private LocalDate endDate;


    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    private Double pricePerNight;

    private Double totalPrice;

    public long getDays() {
        if (startDate == null || endDate == null) return 0;
        return java.time.temporal.ChronoUnit.DAYS.between(startDate, endDate);
    }



}
