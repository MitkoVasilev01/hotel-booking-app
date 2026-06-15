package hotel_booking_app.demo.dtos;

import hotel_booking_app.demo.enums.HotelCategory;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class HotelDisplayDto {

    private String name;

    private String location;

    private HotelCategory category;

    private Set<String> amenities = new HashSet<>();

    private String address;

    private Double pricePerNight;
}
