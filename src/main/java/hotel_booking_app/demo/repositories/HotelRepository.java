package hotel_booking_app.demo.repositories;

import hotel_booking_app.demo.entities.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface HotelRepository extends JpaRepository<Hotel, UUID> {

    List<Hotel> findByLocationContainingIgnoreCase(String location);
}
