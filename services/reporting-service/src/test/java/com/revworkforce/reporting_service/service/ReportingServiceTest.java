package com.revworkforce.reporting_service.service;

import com.revworkforce.reporting_service.client.LeaveClient;
import com.revworkforce.reporting_service.client.PerformanceClient;
import com.revworkforce.reporting_service.client.UserClient;
import com.revworkforce.reporting_service.dto.DashboardResponse;
import com.revworkforce.reporting_service.dto.LeaveSummaryResponse;
import com.revworkforce.reporting_service.dto.PerformanceSummaryResponse;
import com.revworkforce.reporting_service.dto.UserResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReportingServiceTest {

    @Mock
    private UserClient userClient;

    @Mock
    private LeaveClient leaveClient;

    @Mock
    private PerformanceClient performanceClient;

    @InjectMocks
    private ReportingService reportingService;

    @Test
    void getDashboard_shouldAggregateData() {

        UserResponse activeUser = new UserResponse();
        activeUser.setId(1L);
        activeUser.setUsername("john");
        activeUser.setActive(true);

        UserResponse inactiveUser = new UserResponse();
        inactiveUser.setId(2L);
        inactiveUser.setUsername("alex");
        inactiveUser.setActive(false);

        LeaveSummaryResponse leaveSummary =
                new LeaveSummaryResponse();

        leaveSummary.setTotalApplications(20);
        leaveSummary.setApprovedApplications(15);
        leaveSummary.setRejectedApplications(3);
        leaveSummary.setPendingApplications(2);
        leaveSummary.setUtilizationPercentage(75.0);

        PerformanceSummaryResponse performanceSummary =
                new PerformanceSummaryResponse();

        performanceSummary.setTotalReviews(10);
        performanceSummary.setCompletedReviews(8);
        performanceSummary.setPendingReviews(2);
        performanceSummary.setAverageRating(4.2);

        when(userClient.getAllUsers())
                .thenReturn(List.of(activeUser, inactiveUser));

        when(leaveClient.getLeaveSummary())
                .thenReturn(leaveSummary);

        when(performanceClient.getPerformanceSummary())
                .thenReturn(performanceSummary);

        DashboardResponse response =
                reportingService.getDashboard();

        assertNotNull(response);

        assertEquals(2, response.getTotalEmployees());
        assertEquals(1, response.getActiveEmployees());
        assertEquals(1, response.getInactiveEmployees());

        assertNotNull(response.getLeaveSummary());
        assertEquals(
                20,
                response.getLeaveSummary().getTotalApplications()
        );

        assertNotNull(response.getPerformanceSummary());
        assertEquals(
                4.2,
                response.getPerformanceSummary().getAverageRating()
        );

        verify(userClient).getAllUsers();
        verify(leaveClient).getLeaveSummary();
        verify(performanceClient).getPerformanceSummary();
    }

    @Test
    void getEmployeeReport_shouldReturnUsers() {

        UserResponse user = new UserResponse();
        user.setId(1L);
        user.setUsername("john");
        user.setActive(true);

        when(userClient.getAllUsers())
                .thenReturn(List.of(user));

        List<UserResponse> result =
                reportingService.getEmployeeReport();

        assertEquals(1, result.size());
        assertEquals("john", result.get(0).getUsername());

        verify(userClient).getAllUsers();
    }
}