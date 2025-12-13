package hotel_booking_app.demo.entities;

import hotel_booking_app.demo.enums.BookingStatus;
import hotel_booking_app.demo.enums.Role;
import hotel_booking_app.demo.validation.ValidPassword;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;
import java.util.UUID;

@Entity
public class User {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

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

    public User() {}

    public User(String name, String email, BookingStatus bookingStatus, Role role) {
        this.name = name;
        this.email = email;
        this.bookingStatus = bookingStatus;
        this.role = role;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public BookingStatus getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(BookingStatus bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

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
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
}
