package hotel_booking_app.demo.models.binding;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.UUID;

public class ReservationBindingModel {


        private UUID hotelId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
        private LocalDate startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
        private LocalDate endDate;


        public UUID getHotelId() { return hotelId; }
        public void setHotelId(UUID hotelId) { this.hotelId = hotelId; }

        public LocalDate getStartDate() { return startDate; }
        public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

        public LocalDate getEndDate() { return endDate; }
        public void setEndDate(LocalDate endDate) { this.endDate = endDate; }
    }

