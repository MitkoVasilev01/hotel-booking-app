package hotel_booking_app.demo.entities;

import hotel_booking_app.demo.enums.BookingStatus;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.util.UUID;

@Entity
public class Reservation {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

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

    private Double totalPrice;

    public Reservation() {}

    public Reservation(User user, Hotel hotel, LocalDate startDate, LocalDate endDate, BookingStatus status) {
        this.user = user;
        this.hotel = hotel;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    public long getDays() {
        if (startDate == null || endDate == null) return 0;
        return java.time.temporal.ChronoUnit.DAYS.between(startDate, endDate);
    }

    public double getTotalPrice() {
        if (hotel == null) return 0.0;
        long days = getDays();
        if (days < 1) days = 1; // Поне 1 нощувка
        return days * hotel.getPricePerNight();
    }
}
