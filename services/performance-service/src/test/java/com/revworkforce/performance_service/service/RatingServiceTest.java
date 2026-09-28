package com.revworkforce.performance_service.service;

import com.revworkforce.performance_service.entity.PerformanceReview;
import com.revworkforce.performance_service.entity.Rating;
import com.revworkforce.performance_service.repository.RatingRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RatingServiceTest {

    @Mock
    private RatingRepository ratingRepository;

    @InjectMocks
    private RatingService ratingService;

    @Test
    void shouldCreateRating() {
        Rating rating = new Rating();
        PerformanceReview review = new PerformanceReview();
        review.setId(1L);

        rating.setPerformanceReview(review);
        rating.setManagerId(201L);
        rating.setRating(5);

        when(ratingRepository.save(rating)).thenReturn(rating);

        Rating result = ratingService.createRating(rating);

        assertEquals(5, result.getRating());
        verify(ratingRepository).save(rating);
    }

    @Test
    void shouldGetRatingById() {
        Rating rating = new Rating();
        PerformanceReview review = new PerformanceReview();
        review.setId(1L);

        rating.setPerformanceReview(review);
        rating.setRating(5);

        when(ratingRepository.findById(1L))
                .thenReturn(Optional.of(rating));

        Optional<Rating> result = ratingService.getRatingById(1L);

        assertTrue(result.isPresent());
        assertEquals(5, result.get().getRating());
    }

    @Test
    void shouldReturnEmptyWhenRatingDoesNotExist() {
        when(ratingRepository.findById(99L))
                .thenReturn(Optional.empty());

        Optional<Rating> result = ratingService.getRatingById(99L);

        assertTrue(result.isEmpty());
    }

    @Test
    void shouldGetRatingsByReviewId() {
        Rating rating = new Rating();
        PerformanceReview review = new PerformanceReview();
        review.setId(1L);

        rating.setPerformanceReview(review);
        rating.setRating(5);

        when(ratingRepository.findByPerformanceReviewId(1L))
                .thenReturn(List.of(rating));

        List<Rating> result = ratingService.getRatingsByReviewId(1L);

        assertEquals(1, result.size());
        assertEquals(5, result.get(0).getRating());
    }

    @Test
    void shouldReturnEmptyListWhenReviewHasNoRatings() {
        when(ratingRepository.findByPerformanceReviewId(99L))
                .thenReturn(List.of());

        List<Rating> result = ratingService.getRatingsByReviewId(99L);

        assertTrue(result.isEmpty());
    }
}