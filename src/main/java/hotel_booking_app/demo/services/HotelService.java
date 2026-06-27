package hotel_booking_app.demo.services;

import hotel_booking_app.demo.dtos.HotelFormDto;
import hotel_booking_app.demo.entities.Hotel;
import hotel_booking_app.demo.repositories.HotelRepository;
import hotel_booking_app.demo.util.FileUploadUtil;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class HotelService {
    private final HotelRepository hotelRepository;
    private final ModelMapper modelMapper;

    public HotelService(HotelRepository hotelRepository, ModelMapper modelMapper) {
        this.hotelRepository = hotelRepository;
        this.modelMapper = modelMapper;
    }

    public List<Hotel> getAllHotels() {
        return hotelRepository.findAll();
    }


    public Optional<Hotel> getHotelById(UUID id) {
        return hotelRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public HotelFormDto getHotelFormDtoById(UUID id) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid hotel Id:" + id));

        HotelFormDto dto = modelMapper.map(hotel, HotelFormDto.class);

        dto.setAmenitiesText(String.join(", ", hotel.getAmenities()));
        dto.setGalleryText(String.join(", ", hotel.getGalleryImages()));

        return dto;
    }

    @Transactional
    public void addHotel(HotelFormDto dto) throws IOException {
        Hotel hotel = new Hotel();
        hotel.setName(dto.getName());
        hotel.setDescription(dto.getDescription());
        hotel.setCategory(dto.getCategory());
        hotel.setPricePerNight(100.0);
        hotel.setLocation(dto.getLocation());

        if (dto.getMainImageFile() != null && !dto.getMainImageFile().isEmpty()) {
            String fileName = UUID.randomUUID() + "_" + StringUtils.cleanPath(Objects.requireNonNull(dto.getMainImageFile().getOriginalFilename()));
            FileUploadUtil.saveFile("hotel-photos", fileName, dto.getMainImageFile());
            hotel.setImageUrl("/photos/" + fileName);
        }

        Set<String> galleryLinks = new HashSet<>();
        if (dto.getGalleryFiles() != null) {
            for (MultipartFile file : dto.getGalleryFiles()) {
                if (!file.isEmpty()) {
                    String fileName = UUID.randomUUID() + "_" + StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
                    FileUploadUtil.saveFile("hotel-photos", fileName, file);
                    galleryLinks.add("/photos/" + fileName);
                }
            }
        }
        hotel.setGalleryImages(galleryLinks);

        hotel.setAmenities(parseTextToSet(dto.getAmenitiesText()));

        hotelRepository.save(hotel);
    }

    @Transactional
    public void updateHotel(UUID id, HotelFormDto dto) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid hotel Id:" + id));

        hotel.setName(dto.getName());
        hotel.setDescription(dto.getDescription());
        hotel.setCategory(dto.getCategory());
        hotel.setLocation(dto.getLocation());
        hotel.setAddress(dto.getAddress());
        hotel.setPricePerNight(dto.getPricePerNight());

        if (dto.getAmenitiesText() != null && !dto.getAmenitiesText().trim().isEmpty()) {
            hotel.setAmenities(parseTextToSet(dto.getAmenitiesText()));
        }

        if (dto.getGalleryText() != null && !dto.getGalleryText().trim().isEmpty()) {
            hotel.setGalleryImages(parseTextToSet(dto.getGalleryText()));
        }

        hotelRepository.save(hotel);
    }

    public void deleteHotel(UUID id) {
        hotelRepository.deleteById(id);
    }

    private Set<String> parseTextToSet(String text) {
        Set<String> resultSet = new HashSet<>();
        if (text != null && !text.trim().isEmpty()) {
            String[] splitItems = text.split(",");
            for (String item : splitItems) {
                if (!item.trim().isEmpty()) {
                    resultSet.add(item.trim());
                }
            }
        }
        return resultSet;
    }

    public Page<Hotel> getHotelsPaged(String location, int page, int size) {
        // Конструираме автоматична заявка, сортирана по име на хотела възходящо
        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());

        if (location == null || location.isEmpty()) {
            return hotelRepository.findAll(pageable); // Вграден метод в JpaRepository!
        }
        return hotelRepository.findByLocationContainingIgnoreCase(location, pageable);
    }
}