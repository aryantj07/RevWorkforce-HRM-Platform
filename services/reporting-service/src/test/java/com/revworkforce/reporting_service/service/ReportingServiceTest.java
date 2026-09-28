package com.revworkforce.reporting_service.service;

import com.revworkforce.reporting_service.client.EmployeeClient;
import com.revworkforce.reporting_service.client.LeaveClient;
import com.revworkforce.reporting_service.client.PerformanceClient;
import com.revworkforce.reporting_service.dto.DashboardResponse;
import com.revworkforce.reporting_service.dto.EmployeeResponse;
import com.revworkforce.reporting_service.dto.LeaveSummaryResponse;
import com.revworkforce.reporting_service.dto.PerformanceSummaryResponse;
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
    private EmployeeClient employeeClient;

    @Mock
    private LeaveClient leaveClient;

    @Mock
    private PerformanceClient performanceClient;

    @InjectMocks
    private ReportingService reportingService;

    @Test
    void getDashboard_shouldAggregateData() {

        EmployeeResponse activeEmployee = new EmployeeResponse();
        activeEmployee.setId(1L);
        activeEmployee.setFirstName("John");
        activeEmployee.setLastName("Doe");
        activeEmployee.setEmail("john@example.com");
        activeEmployee.setStatus("ACTIVE");

        EmployeeResponse inactiveEmployee = new EmployeeResponse();
        inactiveEmployee.setId(2L);
        inactiveEmployee.setFirstName("Alex");
        inactiveEmployee.setLastName("Smith");
        inactiveEmployee.setEmail("alex@example.com");
        inactiveEmployee.setStatus("INACTIVE");

        EmployeeResponse offboardedEmployee = new EmployeeResponse();
        offboardedEmployee.setId(3L);
        offboardedEmployee.setFirstName("Mike");
        offboardedEmployee.setLastName("Brown");
        offboardedEmployee.setEmail("mike@example.com");
        offboardedEmployee.setStatus("OFFBOARDED");

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

        when(employeeClient.getAllEmployees())
                .thenReturn(List.of(
                        activeEmployee,
                        inactiveEmployee,
                        offboardedEmployee
                ));

        when(leaveClient.getLeaveSummary())
                .thenReturn(leaveSummary);

        when(performanceClient.getPerformanceSummary())
                .thenReturn(performanceSummary);

        DashboardResponse response =
                reportingService.getDashboard();

        assertNotNull(response);

        assertEquals(3, response.getTotalEmployees());
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

        verify(employeeClient).getAllEmployees();
        verify(leaveClient).getLeaveSummary();
        verify(performanceClient).getPerformanceSummary();
    }

    @Test
    void getEmployeeReport_shouldReturnEmployees() {

        EmployeeResponse employee = new EmployeeResponse();
        employee.setId(1L);
        employee.setFirstName("John");
        employee.setLastName("Doe");
        employee.setEmail("john@example.com");
        employee.setStatus("ACTIVE");

        when(employeeClient.getAllEmployees())
                .thenReturn(List.of(employee));

        List<EmployeeResponse> result =
                reportingService.getEmployeeReport();

        assertEquals(1, result.size());
        assertEquals("John", result.get(0).getFirstName());
        assertEquals("ACTIVE", result.get(0).getStatus());

        verify(employeeClient).getAllEmployees();
    }
}