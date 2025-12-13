package hotel_booking_app.demo.controllers;

import hotel_booking_app.demo.entities.User;
import hotel_booking_app.demo.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable UUID id) {
        Optional<User> user = userService.getUserById(id);
        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/profile")
    public String myProfile(Principal principal, Model model) {
        String username = principal.getName();

        User user = userService.findByName(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        model.addAttribute("user", user);

        return "profile";
    }

    @PostMapping("/profile/update")
    public String updateProfile(@RequestParam("phoneNumber") String phoneNumber,
                                @RequestParam("city") String city,
                                Principal principal) {

        User user = userService.findByName(principal.getName())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        user.setPhoneNumber(phoneNumber);
        user.setCity(city);

        userService.createUser(user);

        return "redirect:/users/profile";
    }

    @PostMapping("/profile/change-password")
    public String changePassword(@RequestParam("oldPassword") String oldPassword,
                                 @RequestParam("newPassword") String newPassword,
                                 @RequestParam("confirmPassword") String confirmPassword,
                                 Principal principal,
                                 Model model) {

        User user = userService.findByName(principal.getName()).orElseThrow();

        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {

            return "redirect:/users/profile?error=oldpass";
        }

        if (!newPassword.equals(confirmPassword)) {

            return "redirect:/users/profile?error=match";
        }
        String passwordRegex = "^(?=.*[A-Z])(?=.*\\d)(?=.*[^a-zA-Z0-9]).{8,}$";

        if (!newPassword.matches(passwordRegex)) {
            return "redirect:/users/profile?error=weakpass";
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userService.createUser(user);

        return "redirect:/users/profile?success=pass";
    }
}

