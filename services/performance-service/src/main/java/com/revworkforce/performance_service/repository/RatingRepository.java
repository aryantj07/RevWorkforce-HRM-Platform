package com.revworkforce.performance_service.repository;

import com.revworkforce.performance_service.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, Long> {

    List<Rating> findByPerformanceReviewId(Long reviewId);
}