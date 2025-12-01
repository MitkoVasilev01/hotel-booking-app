package hotel_booking_app.demo.services;

import hotel_booking_app.demo.clients.PaymentClient;
import hotel_booking_app.demo.entities.Hotel;
import hotel_booking_app.demo.entities.Reservation;
import hotel_booking_app.demo.repositories.ReservationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final PaymentClient paymentClient;

    public ReservationService(ReservationRepository reservationRepository, PaymentClient paymentClient) {
        this.reservationRepository = reservationRepository;
        this.paymentClient = paymentClient;
    }

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public Optional<Reservation> getReservationById(UUID id) {
        return reservationRepository.findById(id);
    }

    public Reservation createReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    public void deleteReservation(UUID id) {
        reservationRepository.deleteById(id);
    }

    public void payForReservation(UUID reservationId) {
        // А) Намираме резервацията
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found"));

        paymentClient.processPayment(reservation.getId(), 200.00);

        reservation.setStatus(hotel_booking_app.demo.enums.BookingStatus.CONFIRMED);

        reservationRepository.save(reservation);
    }
}

