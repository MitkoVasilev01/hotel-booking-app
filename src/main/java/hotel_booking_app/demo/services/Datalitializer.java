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

            Hotel hotel3 = new Hotel();
            hotel3.setName("Rila Hotel Borovets");
            hotel3.setLocation("Borovets");
            hotel3.setCategory((HotelCategory.LUXURY));
            hotel3.setAddress("Borovets Resort, Rila Mountain");
            hotel3.setDescription("Модерен ски-ин/ски-аут хотел в подножието на ски пистите в Боровец с невероятен спа център.");
            hotel3.setPricePerNight(180.0);
            hotel3.setImageUrl("https://cf.bstatic.com/xdata/images/hotel/max1024x768/107676800.jpg?k=cd1edab15be27bd3ca6251101cd2194700773da784373c2eb9e5dbb161906981&o=");
            hotel3.setAmenities(Set.of("WiFi", "Ski School", "SPA", "Indoor Pool", "Bar"));
            hotel3.setGalleryImages(Set.of(
                    "https://d3e3f0l0f5xt1p.cloudfront.net/hotelrila-2362005002/cms/cache/v2/63906082c5d67.jpg/900x550/fit/80/8bc6fc68a4493b22ba2c50e24b589029.jpg",
                    "https://d3e3f0l0f5xt1p.cloudfront.net/hotelrila-2362005002/cms/cache/v2/63906082a128d.jpg/1920x1080/fit/80/7b690ef9a1bf80156b824658bc519ca4.jpg",
                    "https://d3e3f0l0f5xt1p.cloudfront.net/hotelrila-2362005002/cms/cache/v2/63906081cc22f.jpg/1920x1080/fit/80/fb43f5c26743f9c47f85fab4cd1880d7.jpg",
                    "https://cf.bstatic.com/xdata/images/hotel/max1024x768/64087326.jpg?k=ae220099d77a614b9b64bcfbe8acbe57b30a05901522ad55927089579eca7594&o=",
                    "https://cf.bstatic.com/xdata/images/hotel/max1024x768/64087180.jpg?k=610c48a6df79dec884cf0806d030f63e0dd151032f345c62a264ce3780ec7068&o="

            ));
            hotelRepository.save(hotel3);

            Hotel hotel4 = new Hotel();
            hotel4.setName("Astera Resort & Spa");
            hotel4.setLocation("Golden Sands");
            hotel4.setCategory(HotelCategory.LUXURY);
            hotel4.setAddress("Main Promenade, Golden Sands");
            hotel4.setDescription("Прекрасен хотел на първа линия до морето с частен плаж, аквапарк и анимация за деца.");
            hotel4.setPricePerNight(130.0);
            hotel4.setImageUrl("https://asterahotel.com/media/image_slide/images/f20670537514b35bc44249b0b028352ffbb01756.jpg");
            hotel4.setAmenities(Set.of("WiFi", "Beach Access", "All Inclusive", "Kids Club", "Gym"));
            hotel4.setGalleryImages(Set.of("https://asterahotel.com/media/image_slide/images/84596a07d265f0d3a463ee03f1b853ccb448e118.jpg",
                    "https://asterahotel.com/media/image_slide/images/729d629ad66d5d58856cd7f399d65758c5f7b3fc.JPG"
            ));
            hotelRepository.save(hotel4);

            log.info("DATABASE SEEDER: Хотелите бяха заредени успешно!");
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