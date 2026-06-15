package hotel_booking_app.demo.services;

import hotel_booking_app.demo.entities.Hotel;
import hotel_booking_app.demo.entities.User;
import hotel_booking_app.demo.enums.BookingStatus;
import hotel_booking_app.demo.enums.HotelCategory;
import hotel_booking_app.demo.enums.Role;
import hotel_booking_app.demo.repositories.HotelRepository;
import hotel_booking_app.demo.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Enumeration;
import java.util.Set;

@Component
@Slf4j
public class Datalitializer implements CommandLineRunner {

    private final HotelRepository hotelRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Datalitializer(HotelRepository hotelRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.hotelRepository = hotelRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (hotelRepository.count() == 0) {

            Hotel hotel1 = new Hotel();
            hotel1.setName("Grand Hotel Sofia");
            hotel1.setLocation("Sofia");
            hotel1.setCategory(HotelCategory.LUXURY);
            hotel1.setAddress("ul. Gurko 1, Sofia");
            hotel1.setDescription("Прекрасен луксозен хотел в сърцето на София с изглед към градската градина и класически ресторант.");
            hotel1.setPricePerNight(150.0);

            hotel1.setImageUrl("https://www.grandhotelsofia.bg/wp-content/uploads/2022/05/grand-hotel-sofia-se1.jpg");
            hotel1.setAmenities(Set.of("WiFi", "SPA", "Parking", "Pool", "Room Service"));
            hotel1.setGalleryImages(Set.of(
                    "https://www.grandhotelsofia.bg/wp-content/uploads/2022/05/grand-hotel-sofia-se1.jpg",
                    "https://www.grandhotelsofia.bg/wp-content/uploads/2022/05/grand-hotel-sofia-28.jpg",
                    "https://www.grandhotelsofia.bg/wp-content/uploads/2022/05/grand-hotel-sofia-14.jpg",
                    "https://www.grandhotelsofia.bg/wp-content/uploads/2022/05/grand-hotel-sofia-se4.jpg",
                    "https://www.grandhotelsofia.bg/wp-content/uploads/2022/05/sofia-theater.jpg",
                    "https://www.grandhotelsofia.bg/wp-content/uploads/2022/05/sofia-thetaer.jpg"
            ));
            hotelRepository.save(hotel1);

            Hotel hotel2 = new Hotel();
            hotel2.setName("Plovdiv Old Town House");
            hotel2.setLocation("Plovdiv");
            hotel2.setCategory(HotelCategory.STANDARD);
            hotel2.setAddress("ul. Saborna 15, Plovdiv");
            hotel2.setDescription("Уютен семеен хотел, разположен в сърцето на Стария град в Пловдив, предлагащ автентична атмосфера.");
            hotel2.setPricePerNight(80.0);
            hotel2.setImageUrl("https://visitbulgaria.com/wp-content/uploads/2020/imported/9_1.jpg");
            hotel2.setAmenities(Set.of("WiFi", "Breakfast", "Family Rooms", "Air Conditioning"));
            hotel2.setGalleryImages(Set.of(
                    "https://visitbulgaria.com/wp-content/uploads/2020/imported/9_1.jpg",
                    "https://visitbulgaria.com/wp-content/uploads/2020/imported/9_2.jpg",
                    "https://cf.bstatic.com/xdata/images/hotel/max1024x768/11679614.jpg?k=a13d37ff944cdd2100a1ba47ad1fbbaad61b73e5c6f1884e4b57fe5eb48a11f5&o=",
                    "https://cf.bstatic.com/xdata/images/hotel/max1024x768/262583745.jpg?k=a0e3ce77e455ff9907d07c6e14ff775fa9dfcf3363bc95154174117c8a9f829f&o="
            ));
            hotelRepository.save(hotel2);


        }
        if (userRepository.count() == 0) {
            User admin = new User();
            admin.setName("admin");
            admin.setEmail("admin@hotelapp.com");
            admin.setPassword(passwordEncoder.encode("admin123."));
            admin.setRole(Role.ADMIN);
            admin.setBookingStatus(BookingStatus.PENDING);
            userRepository.save(admin);

            User client = new User();
            client.setName("user");
            client.setEmail("user@hotelapp.com");
            client.setPassword(passwordEncoder.encode("user123"));
            client.setRole(Role.CLIENT);
            client.setBookingStatus(BookingStatus.PENDING);
            userRepository.save(client);

            log.info("DATABASE SEEDER: Тестовите потребители бяха създадени!");
            log.info("--> АДМИН: admin / admin123.");
            log.info("--> КЛИЕНТ: user / user123");
        }
    }
}