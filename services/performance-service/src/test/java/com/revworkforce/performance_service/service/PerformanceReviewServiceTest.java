package com.revworkforce.performance_service.service;

import com.revworkforce.performance_service.exception.ReviewWorkflowException;
import com.revworkforce.performance_service.entity.PerformanceReview;
import com.revworkforce.performance_service.exception.ReviewNotReadyException;
import com.revworkforce.performance_service.repository.PerformanceReviewRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.revworkforce.performance_service.client.EmployeeClient;
import com.revworkforce.performance_service.client.NotificationClient;
import com.revworkforce.performance_service.dto.EmployeeResponse;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PerformanceReviewServiceTest {

    @Mock
    private PerformanceReviewRepository performanceReviewRepository;

    @InjectMocks
    private PerformanceReviewService performanceReviewService;

    @Mock
    private EmployeeClient employeeClient;

    @Mock
    private NotificationClient notificationClient;


    // ---------------------------------------------------------
    // CREATE REVIEW
    // ---------------------------------------------------------

    @Test
    void shouldCreateReview() {

        PerformanceReview review = new PerformanceReview();

        review.setEmployeeId(101L);
        review.setManagerId(201L);
        review.setReviewPeriod("2026-Q3");
        review.setSelfReview("Good performance");

        when(performanceReviewRepository.save(review))
                .thenReturn(review);

        PerformanceReview result =
                performanceReviewService.createReview(review);

        assertNotNull(result);
        assertEquals(101L, result.getEmployeeId());

        verify(performanceReviewRepository).save(review);
    }


    // ---------------------------------------------------------
    // GET ALL REVIEWS
    // ---------------------------------------------------------

    @Test
    void shouldGetAllReviews() {

        PerformanceReview review1 = new PerformanceReview();
        review1.setId(1L);

        PerformanceReview review2 = new PerformanceReview();
        review2.setId(2L);

        when(performanceReviewRepository.findAll())
                .thenReturn(List.of(review1, review2));

        List<PerformanceReview> result =
                performanceReviewService.getAllReviews();

        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals(2L, result.get(1).getId());

        verify(performanceReviewRepository).findAll();
    }


    // ---------------------------------------------------------
    // GET REVIEW BY ID
    // ---------------------------------------------------------

    @Test
    void shouldGetReviewById() {

        PerformanceReview review = new PerformanceReview();
        review.setId(1L);
        review.setEmployeeId(101L);

        when(performanceReviewRepository.findById(1L))
                .thenReturn(Optional.of(review));

        Optional<PerformanceReview> result =
                performanceReviewService.getReviewById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
        assertEquals(101L, result.get().getEmployeeId());

        verify(performanceReviewRepository).findById(1L);
    }


    // ---------------------------------------------------------
    // GET REVIEW BY ID - NOT FOUND
    // ---------------------------------------------------------

    @Test
    void shouldReturnEmptyWhenReviewDoesNotExist() {

        when(performanceReviewRepository.findById(999L))
                .thenReturn(Optional.empty());

        Optional<PerformanceReview> result =
                performanceReviewService.getReviewById(999L);

        assertTrue(result.isEmpty());

        verify(performanceReviewRepository).findById(999L);
    }


    // ---------------------------------------------------------
    // SELF REVIEW
    // ---------------------------------------------------------

    @Test
    void shouldSubmitSelfReview() {

        PerformanceReview review = new PerformanceReview();
        review.setId(1L);
        review.setStatus("DRAFT");

        when(performanceReviewRepository.findById(1L))
                .thenReturn(Optional.of(review));

        when(performanceReviewRepository.save(review))
                .thenReturn(review);

        Optional<PerformanceReview> result =
                performanceReviewService.submitSelfReview(
                        1L,
                        "I completed all assigned tasks."
                );

        assertTrue(result.isPresent());
        assertEquals(
                "I completed all assigned tasks.",
                result.get().getSelfReview()
        );
        assertEquals(
                "SELF_REVIEW_SUBMITTED",
                result.get().getStatus()
        );

        verify(performanceReviewRepository).findById(1L);
        verify(performanceReviewRepository).save(review);
    }


    // ---------------------------------------------------------
    // SELF REVIEW - NOT FOUND
    // ---------------------------------------------------------

    @Test
    void shouldReturnEmptyWhenSubmittingSelfReviewForMissingReview() {

        when(performanceReviewRepository.findById(999L))
                .thenReturn(Optional.empty());

        Optional<PerformanceReview> result =
                performanceReviewService.submitSelfReview(
                        999L,
                        "Some review"
                );

        assertTrue(result.isEmpty());

        verify(performanceReviewRepository).findById(999L);
        verify(performanceReviewRepository, never())
                .save(any(PerformanceReview.class));
    }


    // ---------------------------------------------------------
    // MANAGER FEEDBACK
    // ---------------------------------------------------------

    @Test
    void shouldSubmitManagerFeedback() {

        PerformanceReview review = new PerformanceReview();

        review.setId(1L);
        review.setEmployeeId(100L);
        review.setStatus("SELF_REVIEW_SUBMITTED");

        when(performanceReviewRepository.findById(1L))
                .thenReturn(Optional.of(review));

        when(performanceReviewRepository.save(review))
                .thenReturn(review);

        EmployeeResponse employeeResponse = new EmployeeResponse();
        employeeResponse.setId(100L);
        employeeResponse.setUserId(1L);

        when(employeeClient.getEmployeeById(100L))
                .thenReturn(employeeResponse);

        Optional<PerformanceReview> result =
                performanceReviewService.submitManagerFeedback(
                        1L,
                        "Good progress. Keep improving."
                );

        assertTrue(result.isPresent());

        assertEquals(
                "Good progress. Keep improving.",
                result.get().getManagerFeedback()
        );

        assertEquals(
                "FEEDBACK_SUBMITTED",
                result.get().getStatus()
        );

        verify(performanceReviewRepository).findById(1L);
        verify(performanceReviewRepository).save(review);

        verify(employeeClient).getEmployeeById(100L);
        verify(notificationClient).createNotification(any());
    }


    // ---------------------------------------------------------
    // MANAGER FEEDBACK - NOT FOUND
    // ---------------------------------------------------------

    @Test
    void shouldReturnEmptyWhenSubmittingFeedbackForMissingReview() {

        when(performanceReviewRepository.findById(999L))
                .thenReturn(Optional.empty());

        Optional<PerformanceReview> result =
                performanceReviewService.submitManagerFeedback(
                        999L,
                        "Feedback"
                );

        assertTrue(result.isEmpty());

        verify(performanceReviewRepository).findById(999L);
        verify(performanceReviewRepository, never())
                .save(any(PerformanceReview.class));
    }


    // ---------------------------------------------------------
    // RATING
    // ---------------------------------------------------------

    @Test
    void shouldSubmitRating() {

        PerformanceReview review = new PerformanceReview();
        review.setId(1L);
        review.setStatus("FEEDBACK_SUBMITTED");

        when(performanceReviewRepository.findById(1L))
                .thenReturn(Optional.of(review));

        when(performanceReviewRepository.save(review))
                .thenReturn(review);

        Optional<PerformanceReview> result =
                performanceReviewService.submitRating(1L, 5);

        assertTrue(result.isPresent());
        assertEquals(5, result.get().getRating());

        verify(performanceReviewRepository).findById(1L);
        verify(performanceReviewRepository).save(review);
    }


    // ---------------------------------------------------------
    // RATING - NOT FOUND
    // ---------------------------------------------------------

    @Test
    void shouldReturnEmptyWhenSubmittingRatingForMissingReview() {

        when(performanceReviewRepository.findById(999L))
                .thenReturn(Optional.empty());

        Optional<PerformanceReview> result =
                performanceReviewService.submitRating(999L, 5);

        assertTrue(result.isEmpty());

        verify(performanceReviewRepository).findById(999L);
        verify(performanceReviewRepository, never())
                .save(any(PerformanceReview.class));
    }


    // ---------------------------------------------------------
    // COMPLETE REVIEW
    // ---------------------------------------------------------

    @Test
    void shouldCompleteReview() {

        PerformanceReview review = new PerformanceReview();

        review.setId(1L);
        review.setSelfReview("Completed my tasks.");
        review.setManagerFeedback("Good work.");
        review.setRating(5);
        review.setStatus("FEEDBACK_SUBMITTED");

        when(performanceReviewRepository.findById(1L))
                .thenReturn(Optional.of(review));

        when(performanceReviewRepository.save(review))
                .thenReturn(review);

        Optional<PerformanceReview> result =
                performanceReviewService.completeReview(1L);

        assertTrue(result.isPresent());
        assertEquals(
                "COMPLETED",
                result.get().getStatus()
        );

        verify(performanceReviewRepository).findById(1L);
        verify(performanceReviewRepository).save(review);
    }


    // ---------------------------------------------------------
    // COMPLETE REVIEW - INCOMPLETE
    // ---------------------------------------------------------

    @Test
    void shouldThrowExceptionWhenReviewIsNotReady() {

        PerformanceReview review = new PerformanceReview();

        review.setId(2L);
        review.setSelfReview(
                "I completed my assigned tasks."
        );

        review.setManagerFeedback(null);
        review.setRating(null);
        review.setStatus("DRAFT");

        when(performanceReviewRepository.findById(2L))
                .thenReturn(Optional.of(review));

        assertThrows(
                ReviewNotReadyException.class,
                () -> performanceReviewService.completeReview(2L)
        );

        verify(performanceReviewRepository).findById(2L);

        verify(performanceReviewRepository, never())
                .save(any(PerformanceReview.class));
    }


    // ---------------------------------------------------------
    // COMPLETE REVIEW - NOT FOUND
    // ---------------------------------------------------------

    @Test
    void shouldReturnEmptyWhenCompletingMissingReview() {

        when(performanceReviewRepository.findById(999L))
                .thenReturn(Optional.empty());

        Optional<PerformanceReview> result =
                performanceReviewService.completeReview(999L);

        assertTrue(result.isEmpty());

        verify(performanceReviewRepository).findById(999L);

        verify(performanceReviewRepository, never())
                .save(any(PerformanceReview.class));
    }


    // ---------------------------------------------------------
    // EMPLOYEE REVIEW HISTORY
    // ---------------------------------------------------------

    @Test
    void shouldGetReviewsByEmployeeId() {

        PerformanceReview review1 = new PerformanceReview();
        review1.setId(1L);
        review1.setEmployeeId(101L);

        PerformanceReview review2 = new PerformanceReview();
        review2.setId(2L);
        review2.setEmployeeId(101L);

        when(performanceReviewRepository.findByEmployeeId(101L))
                .thenReturn(List.of(review1, review2));

        List<PerformanceReview> result =
                performanceReviewService.getReviewsByEmployeeId(101L);

        assertEquals(2, result.size());
        assertEquals(101L, result.get(0).getEmployeeId());
        assertEquals(101L, result.get(1).getEmployeeId());

        verify(performanceReviewRepository)
                .findByEmployeeId(101L);
    }


    // ---------------------------------------------------------
    // EMPLOYEE REVIEW HISTORY - NO REVIEWS
    // ---------------------------------------------------------

    @Test
    void shouldReturnEmptyListWhenEmployeeHasNoReviews() {

        when(performanceReviewRepository.findByEmployeeId(999L))
                .thenReturn(List.of());

        List<PerformanceReview> result =
                performanceReviewService.getReviewsByEmployeeId(999L);

        assertTrue(result.isEmpty());

        verify(performanceReviewRepository)
                .findByEmployeeId(999L);
    }

    @Test
    void shouldRejectManagerFeedbackBeforeSelfReview() {

        PerformanceReview review = new PerformanceReview();
        review.setId(1L);
        review.setStatus("DRAFT");

        when(performanceReviewRepository.findById(1L))
                .thenReturn(Optional.of(review));

        assertThrows(
                ReviewWorkflowException.class,
                () -> performanceReviewService.submitManagerFeedback(
                        1L,
                        "Good performance."
                )
        );

        verify(performanceReviewRepository).findById(1L);

        verify(performanceReviewRepository, never())
                .save(any(PerformanceReview.class));
    }

    @Test
    void shouldRejectRatingBeforeManagerFeedback() {

        PerformanceReview review = new PerformanceReview();
        review.setId(1L);
        review.setStatus("SELF_REVIEW_SUBMITTED");

        when(performanceReviewRepository.findById(1L))
                .thenReturn(Optional.of(review));

        assertThrows(
                ReviewWorkflowException.class,
                () -> performanceReviewService.submitRating(1L, 5)
        );

        verify(performanceReviewRepository).findById(1L);

        verify(performanceReviewRepository, never())
                .save(any(PerformanceReview.class));
    }
}