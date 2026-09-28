package com.revworkforce.performance_service.service;

import com.revworkforce.performance_service.entity.PerformanceReview;
import com.revworkforce.performance_service.repository.PerformanceReviewRepository;
import org.springframework.stereotype.Service;
import com.revworkforce.performance_service.exception.ReviewNotReadyException;
import com.revworkforce.performance_service.exception.ReviewWorkflowException;

import java.util.List;
import java.util.Optional;

@Service
public class PerformanceReviewService {

    private final PerformanceReviewRepository performanceReviewRepository;

    public PerformanceReviewService(PerformanceReviewRepository performanceReviewRepository) {
        this.performanceReviewRepository = performanceReviewRepository;
    }

    public PerformanceReview createReview(PerformanceReview review) {
        return performanceReviewRepository.save(review);
    }

    public List<PerformanceReview> getAllReviews() {
        return performanceReviewRepository.findAll();
    }

    public Optional<PerformanceReview> getReviewById(Long id) {
        return performanceReviewRepository.findById(id);
    }

    public List<PerformanceReview> getReviewsByEmployeeId(Long employeeId) {
        return performanceReviewRepository.findByEmployeeId(employeeId);
    }


    public Optional<PerformanceReview> submitSelfReview(
            Long id,
            String selfReview) {

        Optional<PerformanceReview> optionalReview =
                performanceReviewRepository.findById(id);

        if (optionalReview.isEmpty()) {
            return Optional.empty();
        }

        PerformanceReview review = optionalReview.get();

        review.setSelfReview(selfReview);
        review.setStatus("SELF_REVIEW_SUBMITTED");

        return Optional.of(performanceReviewRepository.save(review));
    }

    public Optional<PerformanceReview> submitManagerFeedback(
            Long id,
            String feedback) {

        Optional<PerformanceReview> optionalReview =
                performanceReviewRepository.findById(id);

        if (optionalReview.isEmpty()) {
            return Optional.empty();
        }

        PerformanceReview review = optionalReview.get();

        if (!"SELF_REVIEW_SUBMITTED".equals(review.getStatus())) {
            throw new ReviewWorkflowException(
                    "Manager feedback can only be submitted after the self-review."
            );
        }

        review.setManagerFeedback(feedback);
        review.setStatus("FEEDBACK_SUBMITTED");

        return Optional.of(performanceReviewRepository.save(review));
    }

    public Optional<PerformanceReview> submitRating(
            Long id,
            Integer rating) {

        Optional<PerformanceReview> optionalReview =
                performanceReviewRepository.findById(id);

        if (optionalReview.isEmpty()) {
            return Optional.empty();
        }

        PerformanceReview review = optionalReview.get();

        if (!"FEEDBACK_SUBMITTED".equals(review.getStatus())) {
            throw new ReviewWorkflowException(
                    "Rating can only be submitted after manager feedback."
            );
        }

        review.setRating(rating);
        review.setStatus("RATING_SUBMITTED");

        return Optional.of(performanceReviewRepository.save(review));
    }

    public Optional<PerformanceReview> completeReview(Long id) {

        Optional<PerformanceReview> optionalReview =
                performanceReviewRepository.findById(id);

        if (optionalReview.isEmpty()) {
            return Optional.empty();
        }

        PerformanceReview review = optionalReview.get();

        if (review.getSelfReview() == null ||
                review.getSelfReview().isBlank() ||
                review.getManagerFeedback() == null ||
                review.getManagerFeedback().isBlank() ||
                review.getRating() == null) {

            throw new ReviewNotReadyException(
                    "Review cannot be completed until self-review, manager feedback, and rating are provided."
            );
        }

        review.setStatus("COMPLETED");

        return Optional.of(performanceReviewRepository.save(review));
    }


}