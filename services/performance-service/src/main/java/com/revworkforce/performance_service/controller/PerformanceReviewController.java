package com.revworkforce.performance_service.controller;

import jakarta.validation.Valid;
import com.revworkforce.performance_service.dto.PerformanceReviewRequest;
import com.revworkforce.performance_service.entity.PerformanceReview;
import com.revworkforce.performance_service.service.PerformanceReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.revworkforce.performance_service.dto.SelfReviewRequest;
import com.revworkforce.performance_service.dto.FeedbackRequest;
import com.revworkforce.performance_service.dto.RatingRequest;

import java.util.List;

@RestController
@RequestMapping("/performance-reviews")
public class PerformanceReviewController {

    private final PerformanceReviewService performanceReviewService;

    public PerformanceReviewController(PerformanceReviewService performanceReviewService) {
        this.performanceReviewService = performanceReviewService;
    }

    @PostMapping
    public ResponseEntity<PerformanceReview> createReview(
            @Valid @RequestBody PerformanceReviewRequest request) {

        PerformanceReview review = new PerformanceReview();

        review.setEmployeeId(request.getEmployeeId());
        review.setManagerId(request.getManagerId());
        review.setReviewPeriod(request.getReviewPeriod());
        review.setSelfReview(request.getSelfReview());
        review.setStatus("DRAFT");

        PerformanceReview savedReview =
                performanceReviewService.createReview(review);

        return new ResponseEntity<>(savedReview, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PerformanceReview>> getAllReviews() {
        return ResponseEntity.ok(
                performanceReviewService.getAllReviews()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<PerformanceReview> getReviewById(
            @PathVariable Long id) {

        return performanceReviewService.getReviewById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() ->
                        ResponseEntity.notFound().build()
                );

    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<PerformanceReview>> getReviewsByEmployeeId(
            @PathVariable Long employeeId) {

        return ResponseEntity.ok(
                performanceReviewService.getReviewsByEmployeeId(employeeId)
        );
    }

    @PutMapping("/{id}/self-review")
    public ResponseEntity<PerformanceReview> submitSelfReview(
            @PathVariable Long id,
            @Valid @RequestBody SelfReviewRequest request) {

        return performanceReviewService
                .submitSelfReview(id, request.getSelfReview())
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/feedback")
    public ResponseEntity<PerformanceReview> submitManagerFeedback(
            @PathVariable Long id,
            @Valid @RequestBody FeedbackRequest request) {

        return performanceReviewService
                .submitManagerFeedback(id, request.getFeedback())
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/rating")
    public ResponseEntity<PerformanceReview> submitRating(
            @PathVariable Long id,
            @Valid @RequestBody RatingRequest request) {

        return performanceReviewService
                .submitRating(id, request.getRating())
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/complete")
    public ResponseEntity<PerformanceReview> completeReview(
            @PathVariable Long id) {

        return performanceReviewService
                .completeReview(id)
                .map(ResponseEntity::ok)
                .orElseGet(() ->
                        ResponseEntity.notFound().build()
                );
    }
}