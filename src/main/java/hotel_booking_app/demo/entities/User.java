package hotel_booking_app.demo.entities;

import hotel_booking_app.demo.enums.BookingStatus;
import hotel_booking_app.demo.enums.Role;
import hotel_booking_app.demo.validation.ValidPassword;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class User extends BaseEntity {


    @Column(unique = true)
    private String name;

    @Column(unique = true)
    private String email;

    @ValidPassword
    @NotBlank
    private String password;

    @Enumerated(EnumType.STRING)
    private BookingStatus bookingStatus;

    @Enumerated(EnumType.STRING)
    private Role role;


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Reservation> reservations;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Review> reviews;

    private String phoneNumber;

    private String city;

    public double getTotalSpent() {
        if (reservations == null || reservations.isEmpty()) {
            return 0.0;
        }

        return reservations.stream()
                .filter(res -> res.getStatus() == hotel_booking_app.demo.enums.BookingStatus.CONFIRMED)
                .mapToDouble(reservation -> {
                    if (reservation.getHotel() == null) return 0.0;

                    long days = java.time.temporal.ChronoUnit.DAYS.between(
                            reservation.getStartDate(), reservation.getEndDate());
                    if (days < 1) days = 1;

                    return days * reservation.getHotel().getPricePerNight();
                })
                .sum();

    }
}
