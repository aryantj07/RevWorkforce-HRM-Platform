package com.revworkforce.performance_service.controller;

import com.revworkforce.performance_service.dto.FeedbackCreateRequest;
import jakarta.validation.Valid;
import com.revworkforce.performance_service.entity.Feedback;
import com.revworkforce.performance_service.service.FeedbackService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.revworkforce.performance_service.repository.PerformanceReviewRepository;
import com.revworkforce.performance_service.entity.PerformanceReview;

import java.util.List;

@RestController
@RequestMapping("/feedback")
public class FeedbackController {

    private final FeedbackService feedbackService;
    private final PerformanceReviewRepository performanceReviewRepository;

    public FeedbackController(
            FeedbackService feedbackService,
            PerformanceReviewRepository performanceReviewRepository) {

        this.feedbackService = feedbackService;
        this.performanceReviewRepository = performanceReviewRepository;
    }

    @PostMapping
    public ResponseEntity<Feedback> createFeedback(
            @Valid @RequestBody FeedbackCreateRequest request) {

        Feedback feedback = new Feedback();

        PerformanceReview performanceReview =
                performanceReviewRepository.findById(request.getReviewId())
                        .orElse(null);

        if (performanceReview == null) {
            return ResponseEntity.notFound().build();
        }

        feedback.setPerformanceReview(performanceReview);
        feedback.setManagerId(request.getManagerId());
        feedback.setComments(request.getComments());

        Feedback createdFeedback = feedbackService.createFeedback(feedback);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdFeedback);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Feedback> getFeedbackById(@PathVariable Long id) {
        return feedbackService.getFeedbackById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/review/{reviewId}")
    public ResponseEntity<List<Feedback>> getFeedbackByReviewId(
            @PathVariable Long reviewId) {

        return ResponseEntity.ok(
                feedbackService.getFeedbackByReviewId(reviewId)
        );
    }
}