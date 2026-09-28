package com.revworkforce.performance_service.controller;

import com.revworkforce.performance_service.dto.GoalRequest;
import com.revworkforce.performance_service.entity.Goal;
import com.revworkforce.performance_service.service.GoalService;
import com.revworkforce.performance_service.dto.GoalUpdateRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/goals")
public class GoalController {

    private final GoalService goalService;

    public GoalController(GoalService goalService) {
        this.goalService = goalService;
    }

    @PostMapping
    public ResponseEntity<Goal> createGoal(
            @Valid @RequestBody GoalRequest request) {

        Goal goal = new Goal();

        goal.setEmployeeId(request.getEmployeeId());
        goal.setTitle(request.getTitle());
        goal.setDescription(request.getDescription());

        Goal savedGoal = goalService.createGoal(goal);

        return new ResponseEntity<>(savedGoal, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Goal>> getAllGoals() {
        return ResponseEntity.ok(
                goalService.getAllGoals()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Goal> getGoalById(
            @PathVariable Long id) {

        return goalService.getGoalById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() ->
                        ResponseEntity.notFound().build()
                );
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<Goal>> getGoalsByEmployeeId(
            @PathVariable Long employeeId) {

        return ResponseEntity.ok(
                goalService.getGoalsByEmployeeId(employeeId)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Goal> updateGoal(
            @PathVariable Long id,
            @Valid @RequestBody GoalUpdateRequest request) {

        return goalService.updateGoal(
                        id,
                        request.getTitle(),
                        request.getDescription(),
                        request.getStatus()
                )
                .map(ResponseEntity::ok)
                .orElseGet(() ->
                        ResponseEntity.notFound().build()
                );
    }
}