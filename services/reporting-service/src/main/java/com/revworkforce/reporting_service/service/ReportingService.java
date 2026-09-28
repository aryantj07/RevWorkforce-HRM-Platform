package com.revworkforce.reporting_service.service;

import com.revworkforce.reporting_service.client.EmployeeClient;
import com.revworkforce.reporting_service.client.LeaveClient;
import com.revworkforce.reporting_service.client.PerformanceClient;
import com.revworkforce.reporting_service.dto.DashboardResponse;
import com.revworkforce.reporting_service.dto.EmployeeResponse;
import com.revworkforce.reporting_service.dto.LeaveSummaryResponse;
import com.revworkforce.reporting_service.dto.PerformanceSummaryResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportingService {

    private final EmployeeClient employeeClient;
    private final LeaveClient leaveClient;
    private final PerformanceClient performanceClient;

    public ReportingService(
            EmployeeClient employeeClient,
            LeaveClient leaveClient,
            PerformanceClient performanceClient) {

        this.employeeClient = employeeClient;
        this.leaveClient = leaveClient;
        this.performanceClient = performanceClient;
    }

    public DashboardResponse getDashboard() {

        List<EmployeeResponse> employees =
                employeeClient.getAllEmployees();

        LeaveSummaryResponse leaveSummary =
                leaveClient.getLeaveSummary();

        PerformanceSummaryResponse performanceSummary =
                performanceClient.getPerformanceSummary();

        long totalEmployees = employees.size();

        long activeEmployees = employees.stream()
                .filter(employee -> "ACTIVE".equals(employee.getStatus()))
                .count();

        long inactiveEmployees = employees.stream()
                .filter(employee -> "INACTIVE".equals(employee.getStatus()))
                .count();

        DashboardResponse response = new DashboardResponse();

        response.setTotalEmployees(totalEmployees);
        response.setActiveEmployees(activeEmployees);
        response.setInactiveEmployees(inactiveEmployees);
        response.setLeaveSummary(leaveSummary);
        response.setPerformanceSummary(performanceSummary);

        return response;
    }

    public List<EmployeeResponse> getEmployeeReport() {
        return employeeClient.getAllEmployees();
    }
}