package hotel_booking_app.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableFeignClients
@EnableScheduling
public class HotelBookingAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(HotelBookingAppApplication.class, args);
	}

}
