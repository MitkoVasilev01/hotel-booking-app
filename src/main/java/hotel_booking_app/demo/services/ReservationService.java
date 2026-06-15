package hotel_booking_app.demo.services;

import hotel_booking_app.demo.clients.PaymentClient;
import hotel_booking_app.demo.dtos.ReservationDto;
import hotel_booking_app.demo.entities.Hotel;
import hotel_booking_app.demo.entities.Reservation;
import hotel_booking_app.demo.entities.User;
import hotel_booking_app.demo.enums.BookingStatus;
import hotel_booking_app.demo.repositories.HotelRepository;
import hotel_booking_app.demo.repositories.ReservationRepository;
import hotel_booking_app.demo.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final PaymentClient paymentClient;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final HotelRepository hotelRepository;


    public ReservationService(ReservationRepository reservationRepository, PaymentClient paymentClient, ModelMapper modelMapper, UserRepository userRepository, HotelRepository hotelRepository) {
        this.reservationRepository = reservationRepository;
        this.paymentClient = paymentClient;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.hotelRepository = hotelRepository;
    }

    @Transactional
    public void createReservation(ReservationDto reservationDto, String username) {
        User user = userRepository.findByName(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + username));

        Hotel hotel = hotelRepository.findById(reservationDto.getHotelId())
                .orElseThrow(() -> new IllegalArgumentException("Hotel not found: " + reservationDto.getHotelId()));


        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setHotel(hotel);
        reservation.setStartDate(reservationDto.getStartDate());
        reservation.setEndDate(reservationDto.getEndDate());
        reservation.setStatus(BookingStatus.PENDING);

        long days = java.time.temporal.ChronoUnit.DAYS.between(reservation.getStartDate(), reservation.getEndDate());
        if (days < 1) days = 1;

        double total = days * hotel.getPricePerNight();
        reservation.setTotalPrice(total);


        reservationRepository.save(reservation);
    }

    public void payForReservation(UUID reservationId) {

        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found"));

        paymentClient.processPayment(reservation.getId(), 200.00);

        reservation.setStatus(hotel_booking_app.demo.enums.BookingStatus.CONFIRMED);

        reservationRepository.save(reservation);
    }
}

