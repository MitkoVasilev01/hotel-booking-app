package hotel_booking_app.demo.config;

import hotel_booking_app.demo.repositories.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        // публични URL-та
                        .requestMatchers("/", "/home", "/users/register", "/users/login","/css/**", "/js/**", "/images/**", "/hotels/**").permitAll()
                        // пример за администратор
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        // пример за обикновен потребител (CLIENT)
                        .requestMatchers("/user/**").hasRole("CLIENT")
                        // всички останали заявки изискват аутентикация
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/users/login")       // Страницата за вход
                        .loginProcessingUrl("/users/login") // Къде се праща POST заявката (съвпада с th:action във формата)
                        .defaultSuccessUrl("/", true)    // Къде отиваме при успех
                        .failureUrl("/users/login?error") // <--- ВАЖНО: При грешка добавя ?error в URL-а
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/users/logout")
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true) // Добра практика: изчиства сесията
                        .deleteCookies("JSESSIONID") // Добра практика: трие бисквитката
                        .permitAll()
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
