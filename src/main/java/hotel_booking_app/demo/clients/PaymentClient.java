package hotel_booking_app.demo.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

// name = името на сървиса, url = къде се намира физически
@FeignClient(name = "payment-service", url = "http://localhost:8081/api/payments")
public interface PaymentClient {

    // Този метод трябва да съвпада с метода в PaymentController на другия проект
    @PostMapping
    Object processPayment(@RequestParam("reservationId") UUID reservationId,
                          @RequestParam("amount") Double amount);
}