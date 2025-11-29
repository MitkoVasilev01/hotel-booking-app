package hotel_booking_app.demo.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PasswordConstraintValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPassword {
    String message() default "Паролата трябва да е поне 8 символа и да съдържа букви, цифри и специални символи.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
