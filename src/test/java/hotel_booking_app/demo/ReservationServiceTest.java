package hotel_booking_app.demo;

import hotel_booking_app.demo.clients.PaymentClient;
import hotel_booking_app.demo.entities.Hotel;
import hotel_booking_app.demo.entities.Reservation;
import hotel_booking_app.demo.enums.BookingStatus;
import hotel_booking_app.demo.repositories.ReservationRepository;
import hotel_booking_app.demo.services.ReservationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private PaymentClient paymentClient;

    @InjectMocks
    private ReservationService reservationService;

    @Test
    void testPayForReservation_ShouldUpdateStatus() {
        UUID resId = UUID.randomUUID();
        Reservation reservation = new Reservation();
        reservation.setId(resId);
        reservation.setStatus(BookingStatus.PENDING);

        Hotel hotel = new Hotel();
        hotel.setPricePerNight(100.0);
        reservation.setHotel(hotel);

        when(reservationRepository.findById(resId)).thenReturn(Optional.of(reservation));

        reservationService.payForReservation(resId);
        assert reservation.getStatus() == BookingStatus.CONFIRMED;

        verify(paymentClient, times(1)).processPayment(eq(resId), anyDouble());

        verify(reservationRepository, times(1)).save(reservation);
    }
}