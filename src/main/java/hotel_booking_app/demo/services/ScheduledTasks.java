package hotel_booking_app.demo.services;

import hotel_booking_app.demo.repositories.ReservationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ScheduledTasks {

    private static final Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);

    private final ReservationRepository reservationRepository;

    public ScheduledTasks(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Scheduled(cron = "0 * * * * *")
    public void reportCurrentReservationCount() {
        long count = reservationRepository.count();
        logger.info("CRON SCHEDULER: В момента в системата има {} резервации.", count);
    }

    @Scheduled(fixedRate = 300000)
    public void performHealthCheck() {
        logger.info("FIXED RATE SCHEDULER: Системата работи нормално към: " + LocalDateTime.now());
    }
}