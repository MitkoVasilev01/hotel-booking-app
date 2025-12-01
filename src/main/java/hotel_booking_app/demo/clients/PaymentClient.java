package hotel_booking_app.demo.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@FeignClient(name = "payment-service", url = "http://localhost:8081/api/payments")
public interface PaymentClient {

    @PostMapping
    Object processPayment(@RequestParam("reservationId") UUID reservationId,
                          @RequestParam("amount") Double amount);
}