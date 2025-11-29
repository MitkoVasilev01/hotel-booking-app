package hotel_booking_app.demo.models.binding;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UserRegisterBindingModel {

    @NotBlank(message = "Потребителското име е задължително!")
    @Size(min = 3, max = 20, message = "Потребителското име трябва да бъде от 3 до 20 символа!")
    @Pattern(regexp = "^[a-zA-Z0-9_.-]+$", message = "Потребителското име трябва да съдържа само латински букви и цифри!")
    private String username;

    @NotBlank(message = "Имейлът е задължителен!")
    @Email(message = "Невалиден имейл!")
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

    public UserRegisterBindingModel() {
    }

    // getters/setters
    public String getUsername() {
        return username;
    }

    public UserRegisterBindingModel setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserRegisterBindingModel setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserRegisterBindingModel setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public UserRegisterBindingModel setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
        return this;
    }

}
