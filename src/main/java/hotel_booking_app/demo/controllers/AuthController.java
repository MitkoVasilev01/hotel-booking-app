package hotel_booking_app.demo.controllers;

import hotel_booking_app.demo.dtos.UserRegisterDto;
import hotel_booking_app.demo.services.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/users")
@Slf4j
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
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
        if (!model.containsAttribute("userRegisterDto")) {
            model.addAttribute("userRegisterDto", new UserRegisterDto());
        }
        return "register";
    }

    @PostMapping("/register")
    public String processRegister(@Valid @ModelAttribute("userRegisterDto") UserRegisterDto userRegisterDto,
                                  BindingResult bindingResult,
                                  Model model) {

        if (bindingResult.hasErrors()) {
            return "register";
        }

        if (!userRegisterDto.getPassword().equals(userRegisterDto.getConfirmPassword())) {
            bindingResult.rejectValue("confirmPassword", "error.confirmPassword", "Паролите не съвпадат!");
            log.warn("Registration failed: Password mismatch.");
            return "register";
        }



        userService.registerUser(userRegisterDto);

        return "redirect:/users/login";
    }


}
