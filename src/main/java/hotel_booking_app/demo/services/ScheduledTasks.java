package hotel_booking_app.demo.services;

import hotel_booking_app.demo.repositories.ReservationRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

    private final ReservationRepository reservationRepository;

    public ScheduledTasks(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Scheduled(cron = "0 * * * * *")
    public void reportCurrentTime() {
        long count = reservationRepository.count();
        System.out.println("⏰ SCHEDULER: В момента в системата има " + count + " резервации.");
    }
}