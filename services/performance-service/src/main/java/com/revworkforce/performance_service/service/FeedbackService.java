    package com.revworkforce.performance_service.service;

    import com.revworkforce.performance_service.entity.Feedback;
    import com.revworkforce.performance_service.repository.FeedbackRepository;
    import org.springframework.stereotype.Service;

    import java.util.List;
    import java.util.Optional;

    @Service
    public class FeedbackService {

        private final FeedbackRepository feedbackRepository;

        public FeedbackService(FeedbackRepository feedbackRepository) {
            this.feedbackRepository = feedbackRepository;
        }

        public Feedback createFeedback(Feedback feedback) {
            return feedbackRepository.save(feedback);
        }

        public Optional<Feedback> getFeedbackById(Long id) {
            return feedbackRepository.findById(id);
        }

        public List<Feedback> getFeedbackByReviewId(Long reviewId) {
            return feedbackRepository.findByPerformanceReviewId(reviewId);
        }
    }