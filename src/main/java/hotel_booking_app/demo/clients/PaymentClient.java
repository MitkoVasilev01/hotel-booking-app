package hotel_booking_app.demo.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@FeignClient(name = "payment-service", url = "${PAYMENT_SERVICE_URL:http://localhost:8081}")
public interface PaymentClient {

    @PostMapping("/api/payments")
    Object processPayment(@RequestParam("reservationId") UUID reservationId,
                          @RequestParam("amount") Double amount);
}