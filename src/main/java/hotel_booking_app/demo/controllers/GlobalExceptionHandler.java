package hotel_booking_app.demo.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public String handleIllegalArgumentException(IllegalArgumentException ex, Model model) {
        log.warn("IllegalArgumentException intercepted: {}", ex.getMessage());

        model.addAttribute("errorTitle", "Ресурсът не е намерен");
        model.addAttribute("errorMessage", ex.getMessage());
        return "error";
    }

    @ExceptionHandler(Exception.class)
    public String handleGeneralException(Exception ex, Model model) {
        log.error("Unexpected exception occurred: ", ex);

        model.addAttribute("errorTitle", "Вътрешна системна грешка");
        model.addAttribute("errorMessage", "Възникна неочаквана грешка в системата ни. Моля, опитайте по-късно или се свържете с поддръжката ни.");
        return "error";
    }
}