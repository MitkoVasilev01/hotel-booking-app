package hotel_booking_app.demo.entities;

import hotel_booking_app.demo.enums.HotelCategory;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.hibernate.annotations.JdbcTypeCode; // <--- Импортни това
import org.hibernate.type.SqlTypes;

import java.util.*;

@Entity
public class Hotel {


    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @JdbcTypeCode(SqlTypes.VARCHAR) // <--- ТОВА Е КЛЮЧЪТ! Прави колоната VARCHAR(36)
    private UUID id;

    private String name;
    private String location;

    @Enumerated(EnumType.STRING)
    private HotelCategory category;

    @Column(columnDefinition = "TEXT") // За да събере дълги линкове
    private String imageUrl;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Review> reviews = new ArrayList<>(); // Инициализираме го, за да не е null

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "hotel_amenities", joinColumns = @JoinColumn(name = "hotel_id"))
    @Column(name = "amenity")
    private Set<String> amenities = new HashSet<>(); // Беше List, става Set

    private String address;

    @Column(columnDefinition = "TEXT") // Позволява дълъг текст
    private String description;

    // Списък за допълнителни снимки (Галерия)
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "hotel_images", joinColumns = @JoinColumn(name = "hotel_id"))
    @Column(name = "image_url")
    private Set<String> galleryImages = new HashSet<>();

    public Hotel() {

    }

    public Hotel(String name, String location, HotelCategory category) {

        this.name = name;
        this.location = location;
        this.category = category;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public HotelCategory getCategory() {
        return category;
    }

    public void setCategory(HotelCategory category) {
        this.category = category;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }





    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<String> getAmenities() {
        return amenities;
    }

    public void setAmenities(Set<String> amenities) {
        this.amenities = amenities;
    }

    public Set<String> getGalleryImages() {
        return galleryImages;
    }

    public void setGalleryImages(Set<String> galleryImages) {
        this.galleryImages = galleryImages;
    }
}
