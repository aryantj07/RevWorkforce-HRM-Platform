package com.revworkforce.performance_service.repository;

import java.util.List;
import com.revworkforce.performance_service.entity.Goal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoalRepository extends JpaRepository<Goal, Long> {
    List<Goal> findByEmployeeId(Long employeeId);
}