package hotel_booking_app.demo.repositories;

import hotel_booking_app.demo.entities.Hotel;
import hotel_booking_app.demo.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ReviewRepository extends JpaRepository<Review, UUID> {
}
