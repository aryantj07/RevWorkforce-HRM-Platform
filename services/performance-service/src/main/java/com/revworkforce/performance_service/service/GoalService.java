package com.revworkforce.performance_service.service;

import com.revworkforce.performance_service.entity.Goal;
import com.revworkforce.performance_service.repository.GoalRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GoalService {

    private final GoalRepository goalRepository;

    public GoalService(GoalRepository goalRepository) {
        this.goalRepository = goalRepository;
    }

    public Goal createGoal(Goal goal) {
        goal.setStatus("NOT_STARTED");
        return goalRepository.save(goal);
    }

    public List<Goal> getAllGoals() {
        return goalRepository.findAll();
    }

    public Optional<Goal> getGoalById(Long id) {
        return goalRepository.findById(id);
    }

    public List<Goal> getGoalsByEmployeeId(Long employeeId) {
        return goalRepository.findByEmployeeId(employeeId);
    }

    public Optional<Goal> updateGoal(
            Long id,
            String title,
            String description,
            String status) {

        Optional<Goal> optionalGoal = goalRepository.findById(id);

        if (optionalGoal.isEmpty()) {
            return Optional.empty();
        }

        Goal goal = optionalGoal.get();

        goal.setTitle(title);
        goal.setDescription(description);
        goal.setStatus(status);

        return Optional.of(goalRepository.save(goal));
    }
}