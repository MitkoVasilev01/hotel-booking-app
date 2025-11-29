package hotel_booking_app.demo.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String> {

    private static final Pattern COMPLEXITY_PATTERN = Pattern.compile(
            "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[^a-zA-Z0-9]).{8,}$"
    );

    private static final String[] FORBIDDEN_SEQUENCES = {
            "12345678", "87654321",
            "abcdef", "fedcba",
            "qwerty", "ytrewq"
    };

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if (password == null) {
            return false;
        }

        // Проверка за сложност
        if (!COMPLEXITY_PATTERN.matcher(password).matches()) {
            return false;
        }

        // Проверка за забранени последователности
        String lower = password.toLowerCase();
        for (String seq : FORBIDDEN_SEQUENCES) {
            if (lower.contains(seq)) {
                return false;
            }
        }

        return true;
    }


}
