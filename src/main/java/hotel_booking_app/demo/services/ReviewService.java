package hotel_booking_app.demo.services;

import hotel_booking_app.demo.entities.Hotel;
import hotel_booking_app.demo.entities.Review;
import hotel_booking_app.demo.entities.User;
import hotel_booking_app.demo.repositories.HotelRepository;
import hotel_booking_app.demo.repositories.ReviewRepository;
import hotel_booking_app.demo.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final HotelRepository hotelRepository;
    private final UserRepository userRepository;

    public ReviewService(ReviewRepository reviewRepository, HotelRepository hotelRepository, UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.hotelRepository = hotelRepository;
        this.userRepository = userRepository;
    }

    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    public Optional<Review> getReviewById(UUID id) {
        return reviewRepository.findById(id);
    }

    @Transactional
    public Review createReview(Review review) {
        return reviewRepository.save(review);
    }

    public void createReview(UUID hotelId, String username, int rating, String comment) {
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid hotel Id"));

        User user = userRepository.findByName(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Review newReview = new Review();
        newReview.setHotel(hotel);
        newReview.setUser(user);
        newReview.setRating(rating);
        newReview.setComment(comment);

        reviewRepository.save(newReview);
    }
    @Transactional
    public void deleteReview(UUID id) {
        reviewRepository.deleteById(id);
    }
    }

