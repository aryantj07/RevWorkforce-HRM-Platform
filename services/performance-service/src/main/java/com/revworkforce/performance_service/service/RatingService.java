package com.revworkforce.performance_service.service;

import com.revworkforce.performance_service.entity.Rating;
import com.revworkforce.performance_service.repository.RatingRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RatingService {

    private final RatingRepository ratingRepository;

    public RatingService(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    public Rating createRating(Rating rating) {
        return ratingRepository.save(rating);
    }

    public Optional<Rating> getRatingById(Long id) {
        return ratingRepository.findById(id);
    }

    public List<Rating> getRatingsByReviewId(Long reviewId) {
        return ratingRepository.findByPerformanceReviewId(reviewId);
    }
}