package hotel_booking_app.demo;

import hotel_booking_app.demo.entities.Hotel;
import hotel_booking_app.demo.enums.HotelCategory;
import hotel_booking_app.demo.repositories.HotelRepository;
import hotel_booking_app.demo.services.HotelService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class HotelServiceTest {

    @Mock
    private HotelRepository hotelRepository;

    @InjectMocks
    private HotelService hotelService;

    private Hotel testHotel;

    @BeforeEach
    void setUp() {
        testHotel = new Hotel();
        testHotel.setId(UUID.randomUUID());
        testHotel.setName("Test Hotel");
        testHotel.setPricePerNight(100.0);
        testHotel.setCategory(HotelCategory.LUXURY);
    }

    @Test
    void testCreateHotel() {
        when(hotelRepository.save(any(Hotel.class))).thenReturn(testHotel);

        Hotel created = hotelService.createHotel(testHotel);

        Assertions.assertNotNull(created);
        Assertions.assertEquals("Test Hotel", created.getName());
    }

    @Test
    void testGetAllHotels() {
        when(hotelRepository.findAll()).thenReturn(List.of(testHotel));

        List<Hotel> hotels = hotelService.getAllHotels();

        Assertions.assertEquals(1, hotels.size());
    }
}