package hotel_booking_app.demo.repositories;

import hotel_booking_app.demo.entities.Hotel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface HotelRepository extends JpaRepository<Hotel, UUID> {

    Page<Hotel> findByLocationContainingIgnoreCase(String location, Pageable pageable);
}
