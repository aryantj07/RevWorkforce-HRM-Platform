package com.revworkforce.performance_service.service;

import com.revworkforce.performance_service.entity.Goal;
import com.revworkforce.performance_service.repository.GoalRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GoalServiceTest {

    @Mock
    private GoalRepository goalRepository;

    @InjectMocks
    private GoalService goalService;

    @Test
    void shouldCreateGoal() {

        Goal goal = new Goal();

        goal.setEmployeeId(101L);
        goal.setTitle("Improve Java Skills");
        goal.setDescription("Complete Java training");

        when(goalRepository.save(goal)).thenReturn(goal);

        Goal result = goalService.createGoal(goal);

        assertEquals("NOT_STARTED", result.getStatus());
        verify(goalRepository).save(goal);
    }

    @Test
    void shouldUpdateGoal() {

        Goal goal = new Goal();

        goal.setId(1L);
        goal.setEmployeeId(101L);
        goal.setTitle("Old Title");
        goal.setDescription("Old Description");
        goal.setStatus("NOT_STARTED");

        when(goalRepository.findById(1L))
                .thenReturn(Optional.of(goal));

        when(goalRepository.save(goal))
                .thenReturn(goal);

        Optional<Goal> result = goalService.updateGoal(
                1L,
                "New Title",
                "New Description",
                "IN_PROGRESS"
        );

        assertTrue(result.isPresent());
        assertEquals("New Title", result.get().getTitle());
        assertEquals("New Description", result.get().getDescription());
        assertEquals("IN_PROGRESS", result.get().getStatus());

        verify(goalRepository).findById(1L);
        verify(goalRepository).save(goal);
    }

    @Test
    void shouldReturnEmptyWhenGoalDoesNotExist() {

        when(goalRepository.findById(999L))
                .thenReturn(Optional.empty());

        Optional<Goal> result = goalService.updateGoal(
                999L,
                "New Title",
                "New Description",
                "IN_PROGRESS"
        );

        assertTrue(result.isEmpty());

        verify(goalRepository).findById(999L);
        verify(goalRepository, never()).save(any(Goal.class));
    }

    @Test
    void shouldGetGoalById() {

        Goal goal = new Goal();

        goal.setId(1L);
        goal.setEmployeeId(101L);
        goal.setTitle("Improve Java Skills");
        goal.setDescription("Complete Java training");
        goal.setStatus("IN_PROGRESS");

        when(goalRepository.findById(1L))
                .thenReturn(Optional.of(goal));

        Optional<Goal> result = goalService.getGoalById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
        assertEquals("Improve Java Skills", result.get().getTitle());
        assertEquals("IN_PROGRESS", result.get().getStatus());

        verify(goalRepository).findById(1L);
    }

    @Test
    void shouldGetGoalsByEmployeeId() {

        Goal goal = new Goal();

        goal.setId(1L);
        goal.setEmployeeId(101L);
        goal.setTitle("Improve Java Skills");
        goal.setDescription("Complete Java training");
        goal.setStatus("IN_PROGRESS");

        when(goalRepository.findByEmployeeId(101L))
                .thenReturn(java.util.List.of(goal));

        java.util.List<Goal> result =
                goalService.getGoalsByEmployeeId(101L);

        assertEquals(1, result.size());
        assertEquals(101L, result.get(0).getEmployeeId());
        assertEquals("Improve Java Skills", result.get(0).getTitle());

        verify(goalRepository).findByEmployeeId(101L);
    }
}