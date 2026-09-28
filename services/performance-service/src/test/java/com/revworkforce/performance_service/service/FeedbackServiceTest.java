package com.revworkforce.performance_service.service;

import com.revworkforce.performance_service.entity.Feedback;
import com.revworkforce.performance_service.repository.FeedbackRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.revworkforce.performance_service.entity.PerformanceReview;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FeedbackServiceTest {

    @Mock
    private FeedbackRepository feedbackRepository;

    @InjectMocks
    private FeedbackService feedbackService;

    @Test
    void shouldCreateFeedback() {
        Feedback feedback = new Feedback();
        PerformanceReview review = new PerformanceReview();
        review.setId(1L);

        feedback.setPerformanceReview(review);
        feedback.setManagerId(201L);
        feedback.setComments("Good performance");

        when(feedbackRepository.save(feedback)).thenReturn(feedback);

        Feedback result = feedbackService.createFeedback(feedback);

        assertEquals("Good performance", result.getComments());
        verify(feedbackRepository).save(feedback);
    }

    @Test
    void shouldGetFeedbackById() {
        Feedback feedback = new Feedback();
        PerformanceReview review = new PerformanceReview();
        review.setId(1L);

        feedback.setPerformanceReview(review);
        feedback.setComments("Good performance");

        when(feedbackRepository.findById(1L)).thenReturn(Optional.of(feedback));

        Optional<Feedback> result = feedbackService.getFeedbackById(1L);

        assertTrue(result.isPresent());
        assertEquals("Good performance", result.get().getComments());
    }

    @Test
    void shouldReturnEmptyWhenFeedbackDoesNotExist() {
        when(feedbackRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<Feedback> result = feedbackService.getFeedbackById(99L);

        assertTrue(result.isEmpty());
    }

    @Test
    void shouldGetFeedbackByReviewId() {
        Feedback feedback = new Feedback();
        PerformanceReview review = new PerformanceReview();
        review.setId(1L);

        feedback.setPerformanceReview(review);
        feedback.setComments("Good performance");

        when(feedbackRepository.findByPerformanceReviewId(1L))
                .thenReturn(List.of(feedback));

        List<Feedback> result = feedbackService.getFeedbackByReviewId(1L);

        assertEquals(1, result.size());
        assertEquals("Good performance", result.get(0).getComments());
    }

    @Test
    void shouldReturnEmptyListWhenReviewHasNoFeedback() {
        when(feedbackRepository.findByPerformanceReviewId(99L))
                .thenReturn(List.of());

        List<Feedback> result = feedbackService.getFeedbackByReviewId(99L);

        assertTrue(result.isEmpty());
    }
}