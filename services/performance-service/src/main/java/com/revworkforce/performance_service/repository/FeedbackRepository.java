package com.revworkforce.performance_service.repository;

import com.revworkforce.performance_service.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

    List<Feedback> findByPerformanceReviewId(Long reviewId);
}