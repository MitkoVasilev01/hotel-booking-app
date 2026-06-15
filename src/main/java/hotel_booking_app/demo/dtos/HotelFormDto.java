package hotel_booking_app.demo.dtos;

import hotel_booking_app.demo.enums.HotelCategory;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
public class HotelFormDto {

    private UUID id;
    private String name;
    private String location;
    private HotelCategory category;
    private String amenitiesText;
    private String address;
    private String description;
    private Set<String> galleryImages = new HashSet<>();
    private MultipartFile mainImageFile;
    private MultipartFile[] galleryFiles;

    @jakarta.validation.constraints.NotNull(message = "Цената е задължителна!")
    @jakarta.validation.constraints.Min(value = 0, message = "Цената трябва да бъде положително число!")
    private Double pricePerNight;
    private String GalleryText;

}
