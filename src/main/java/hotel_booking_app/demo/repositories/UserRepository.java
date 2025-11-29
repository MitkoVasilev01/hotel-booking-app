package hotel_booking_app.demo.repositories;

import hotel_booking_app.demo.entities.Hotel;
import hotel_booking_app.demo.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByName(String name);
    Optional<User> findByEmail(String email);
}
