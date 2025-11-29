package hotel_booking_app.demo.services;

import hotel_booking_app.demo.entities.Hotel;
import hotel_booking_app.demo.repositories.HotelRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class HotelService {
    private final HotelRepository hotelRepository;

    public HotelService(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    public List<Hotel> getAllHotels() {
        return hotelRepository.findAll();
    }
    @Transactional(readOnly = true)
    public Optional<Hotel> getHotelById(UUID id) {
        return hotelRepository.findById(id);
    }

    public Hotel createHotel(Hotel hotel) {
        return hotelRepository.save(hotel);
    }

    public void deleteHotel(UUID id) {
        hotelRepository.deleteById(id);
    }

    public List<Hotel> searchHotels(String location) {
        if (location == null || location.isEmpty()) {
            return hotelRepository.findAll(); // Ако няма търсене, връща всички
        }
        return hotelRepository.findByLocationContainingIgnoreCase(location);
    }
}
