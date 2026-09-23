package com.revworkforce.reporting_service.service;

import com.revworkforce.reporting_service.client.LeaveClient;
import com.revworkforce.reporting_service.client.PerformanceClient;
import com.revworkforce.reporting_service.client.UserClient;
import com.revworkforce.reporting_service.dto.DashboardResponse;
import com.revworkforce.reporting_service.dto.LeaveSummaryResponse;
import com.revworkforce.reporting_service.dto.PerformanceSummaryResponse;
import com.revworkforce.reporting_service.dto.UserResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportingService {

    private final UserClient userClient;
    private final LeaveClient leaveClient;
    private final PerformanceClient performanceClient;

    public ReportingService(
            UserClient userClient,
            LeaveClient leaveClient,
            PerformanceClient performanceClient) {

        this.userClient = userClient;
        this.leaveClient = leaveClient;
        this.performanceClient = performanceClient;
    }

    public DashboardResponse getDashboard() {

        List<UserResponse> users = userClient.getAllUsers();

        LeaveSummaryResponse leaveSummary =
                leaveClient.getLeaveSummary();

        PerformanceSummaryResponse performanceSummary =
                performanceClient.getPerformanceSummary();

        long totalEmployees = users.size();

        long activeEmployees = users.stream()
                .filter(UserResponse::isActive)
                .count();

        long inactiveEmployees =
                totalEmployees - activeEmployees;

        DashboardResponse response = new DashboardResponse();

        response.setTotalEmployees(totalEmployees);
        response.setActiveEmployees(activeEmployees);
        response.setInactiveEmployees(inactiveEmployees);
        response.setLeaveSummary(leaveSummary);
        response.setPerformanceSummary(performanceSummary);

        return response;
    }

    public List<UserResponse> getEmployeeReport() {
        return userClient.getAllUsers();
    }
}