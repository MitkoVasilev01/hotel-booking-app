package hotel_booking_app.demo.services;

import hotel_booking_app.demo.entities.User;
import hotel_booking_app.demo.enums.BookingStatus;
import hotel_booking_app.demo.enums.Role;
import hotel_booking_app.demo.dtos.UserRegisterDto;
import hotel_booking_app.demo.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }

        public User registerUser(UserRegisterDto userRegisterDto) {
        User user = modelMapper.map(userRegisterDto, User.class);
        user.setPassword(passwordEncoder.encode(userRegisterDto.getPassword()));
        user.setRole(Role.CLIENT);
        user.setBookingStatus(BookingStatus.PENDING);
      return userRepository.save(user);

    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(UUID id) {
        return userRepository.findById(id);
    }

    public void updateUser(String username ,String phoneNumber, String city) {
        User user = userRepository.findByName(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        user.setPhoneNumber(phoneNumber);
        user.setCity(city);

        userRepository.save(user);

    }

    public void changePassword(String username, String oldPassword, String newPassword, String confirmPassword) {
        User user = userRepository.findByName(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new IllegalArgumentException("oldpass");
        }

        if (!newPassword.equals(confirmPassword)) {
            throw new IllegalArgumentException("match");
        }

        String passwordRegex = "^(?=.*[A-Z])(?=.*\\d)(?=.*[^a-zA-Z0-9]).{8,}$";
        if (!newPassword.matches(passwordRegex)) {
            throw new IllegalArgumentException("weakpass");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    public Optional<User> findByName(String name) {
        return userRepository.findByName(name);
    }

    public void deleteUser(UUID id) {
        userRepository.deleteById(id);
    }

}


