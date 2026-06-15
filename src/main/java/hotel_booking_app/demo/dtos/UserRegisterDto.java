package hotel_booking_app.demo.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegisterDto {

    @NotBlank(message = "Потребителското име е задължително!")
    @Size(min = 3, max = 20, message = "Потребителското име трябва да бъде от 3 до 20 символа!")
    @Pattern(regexp = "^[a-zA-Z0-9_.-]+$", message = "Потребителското име трябва да съдържа само латински букви и цифри!")
    private String name;

    @NotBlank(message = "Имейлът е задължителен!")
    @Email
    @Pattern(
            regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$",
            message = "Невалиден имейл адрес!")
    private String email;

    @NotBlank(message = "Паролата е задължителна!")
    @Size(min = 8, message = "Паролата трябва да е поне с 8 символа!")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[^a-zA-Z0-9]).{8,}$",
            message = "Паролата трябва да съдържа поне една главна буква, цифра и символ!"
    )
    private String password;

    @NotBlank(message = "Потвърждението на паролата е задължително!")
    private String confirmPassword;



}
