package com.revworkforce.performance_service.controller;

import com.revworkforce.performance_service.dto.RatingCreateRequest;
import com.revworkforce.performance_service.entity.PerformanceReview;
import com.revworkforce.performance_service.entity.Rating;
import com.revworkforce.performance_service.repository.PerformanceReviewRepository;
import com.revworkforce.performance_service.service.RatingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ratings")
public class RatingController {

    private final RatingService ratingService;
    private final PerformanceReviewRepository performanceReviewRepository;

    public RatingController(
            RatingService ratingService,
            PerformanceReviewRepository performanceReviewRepository) {

        this.ratingService = ratingService;
        this.performanceReviewRepository = performanceReviewRepository;
    }

    @PostMapping
    public ResponseEntity<Rating> createRating(
            @Valid @RequestBody RatingCreateRequest request) {

        PerformanceReview performanceReview =
                performanceReviewRepository.findById(request.getReviewId())
                        .orElse(null);

        if (performanceReview == null) {
            return ResponseEntity.notFound().build();
        }

        Rating rating = new Rating();

        rating.setPerformanceReview(performanceReview);
        rating.setManagerId(request.getManagerId());
        rating.setRating(request.getRating());

        Rating createdRating = ratingService.createRating(rating);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdRating);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Rating> getRatingById(@PathVariable Long id) {

        return ratingService.getRatingById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/review/{reviewId}")
    public ResponseEntity<List<Rating>> getRatingsByReviewId(
            @PathVariable Long reviewId) {

        return ResponseEntity.ok(
                ratingService.getRatingsByReviewId(reviewId)
        );
    }
}