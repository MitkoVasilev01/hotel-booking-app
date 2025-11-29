package hotel_booking_app.demo.services;

import hotel_booking_app.demo.entities.User;
import hotel_booking_app.demo.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service // Тази анотация е важна, за да може Spring да го намери
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1. Търсим потребителя
        User user = userRepository.findByName(username)
                .orElseThrow(() -> {
                    System.out.println("Login опит неуспешен: Потребителят " + username + " не е намерен.");
                    return new UsernameNotFoundException("User not found: " + username);
                });

        // 2. Debug принт - да видим дали паролата в базата е хаширана
        System.out.println("Login опит за: " + username);
        System.out.println("Парола в базата (hash): " + user.getPassword());

        // 3. Връщаме UserDetails към Spring Security
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getName())
                .password(user.getPassword())
                // Взимаме ролята динамично от ентитито (напр. CLIENT, ADMIN)
                .roles(user.getRole().name())
                .build();
    }
}