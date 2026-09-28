package com.revworkforce.performance_service.repository;

import java.util.List;

import com.revworkforce.performance_service.entity.PerformanceReview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PerformanceReviewRepository extends JpaRepository<PerformanceReview, Long> {
    List<PerformanceReview> findByEmployeeId(Long employeeId);
}