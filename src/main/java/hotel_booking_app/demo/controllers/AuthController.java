package hotel_booking_app.demo.controllers;

import hotel_booking_app.demo.models.binding.UserRegisterBindingModel;
import hotel_booking_app.demo.services.UserService;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class AuthController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }
    @GetMapping("/login")
    public String login(@RequestParam(value = "redirect", required = false) String redirect,
                        jakarta.servlet.http.HttpSession session) {
        if (redirect != null && !redirect.isEmpty()) {
            session.setAttribute("redirectAfterLogin", redirect);
        }

        return "login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        if (!model.containsAttribute("userRegisterBindingModel")) {
            model.addAttribute("userRegisterBindingModel", new UserRegisterBindingModel());
        }
        return "register";
    }

    @PostMapping("/register")
    public String processRegister(@Valid @ModelAttribute("userRegisterBindingModel") UserRegisterBindingModel userRegisterBindingModel,
                                  BindingResult bindingResult,
                                  Model model) {



        if (!userRegisterBindingModel.getPassword().equals(userRegisterBindingModel.getConfirmPassword())) {
            System.out.println("Грешка: Паролите не съвпадат");
            bindingResult.rejectValue("confirmPassword", "error.userRegisterBindingModel", "Паролите не съвпадат!");
        }

        if (userService.findByName(userRegisterBindingModel.getUsername()).isPresent()) {
            System.out.println("Грешка: Името е заето");
            bindingResult.rejectValue("username", "error.userRegisterBindingModel", "Потребителското име вече е заето!");
        }

        if (userService.findByEmail(userRegisterBindingModel.getEmail()).isPresent()) {
            System.out.println("Грешка: Имейлът е зает");
            bindingResult.rejectValue("email", "error.userRegisterBindingModel", "Този имейл вече е регистриран!");
        }

        if (bindingResult.hasErrors()) {
            return "register";
        }

        hotel_booking_app.demo.entities.User user = new hotel_booking_app.demo.entities.User();
        user.setName(userRegisterBindingModel.getUsername());
        user.setEmail(userRegisterBindingModel.getEmail());
        user.setPassword(passwordEncoder.encode(userRegisterBindingModel.getPassword()));
        user.setRole(hotel_booking_app.demo.enums.Role.CLIENT);
        user.setBookingStatus(hotel_booking_app.demo.enums.BookingStatus.PENDING);

        userService.createUser(user);

        return "redirect:/users/login";
    }

    private boolean isValidPassword(String password) {
        String regex = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[^A-Za-z\\d]).{8,}$";

        return password.matches(regex);
    }


}
