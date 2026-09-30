package com.revworkforce.web.controller;

import com.revworkforce.web.model.ApiResponse;
import com.revworkforce.web.model.Employee;
import com.revworkforce.web.model.LeaveBalance;
import com.revworkforce.web.model.LeaveResponse;
import com.revworkforce.web.model.Notification;
import com.revworkforce.web.model.PerformanceReview;
import com.revworkforce.web.service.GatewayClient;

import jakarta.servlet.http.HttpSession;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class DashboardController {

    private final GatewayClient gatewayClient;

    public DashboardController(GatewayClient gatewayClient) {
        this.gatewayClient = gatewayClient;
    }

    @GetMapping("/employee/dashboard")
    public String employeeDashboard(
            HttpSession session,
            Model model) {

        if (session.getAttribute("token") == null) {
            return "redirect:/login";
        }

        try {

            Long userId = (Long) session.getAttribute("userId");

            if (userId == null) {
                return "redirect:/login";
            }

            // Resolve logged-in user's employee ID
            Employee employee =
                    gatewayClient
                            .authenticatedGet(
                                    "/employee-management-service/api/employees/user/" + userId,
                                    session)
                            .retrieve()
                            .body(Employee.class);

            if (employee == null || employee.getId() == null) {
                model.addAttribute("leaveBalance", 0);
                model.addAttribute("pendingLeaves", 0);
                model.addAttribute("performanceReviews", 0);
                model.addAttribute("unreadNotifications", 0);

                return "employee/dashboard";
            }

            Long employeeId = employee.getId();

            // Leave balances
            ApiResponse<List<LeaveBalance>> balanceResponse =
                    gatewayClient
                            .authenticatedGet(
                                    "/leave-service/api/v1/leave-balances/employee/"
                                            + employeeId,
                                    session)
                            .retrieve()
                            .body(
                                    new ParameterizedTypeReference<
                                            ApiResponse<List<LeaveBalance>>>() {
                                    }
                            );

            int leaveBalance = 0;

            if (balanceResponse != null
                    && balanceResponse.getData() != null) {

                leaveBalance = balanceResponse.getData()
                        .stream()
                        .mapToInt(LeaveBalance::getRemainingDays)
                        .sum();
            }

            // Leave history / pending leaves
            ApiResponse<List<LeaveResponse>> leaveResponse =
                    gatewayClient
                            .authenticatedGet(
                                    "/leave-service/api/v1/leave-requests/employee/"
                                            + employeeId,
                                    session)
                            .retrieve()
                            .body(
                                    new ParameterizedTypeReference<
                                            ApiResponse<List<LeaveResponse>>>() {
                                    }
                            );

            int pendingLeaves = 0;

            if (leaveResponse != null
                    && leaveResponse.getData() != null) {

                pendingLeaves = (int) leaveResponse.getData()
                        .stream()
                        .filter(leave ->
                                leave.getStatus() != null
                                        && "PENDING".equalsIgnoreCase(
                                        leave.getStatus().toString()))
                        .count();
            }

            // Performance reviews
            List<PerformanceReview> performanceResponse =
                    gatewayClient
                            .authenticatedGet(
                                    "/performance-service/performance-reviews/employee/"
                                            + employeeId,
                                    session)
                            .retrieve()
                            .body(
                                    new ParameterizedTypeReference<
                                            List<PerformanceReview>>() {
                                    }
                            );

            int performanceReviews = 0;

            if (performanceResponse != null) {
                performanceReviews = performanceResponse.size();
            }

            // Unread notifications
            ApiResponse<Long> notificationResponse =
                    gatewayClient
                            .authenticatedGet(
                                    "/notification-service/api/v1/notifications/user/"
                                            + userId
                                            + "/unread-count",
                                    session)
                            .retrieve()
                            .body(
                                    new ParameterizedTypeReference<
                                            ApiResponse<Long>>() {
                                    }
                            );

            long unreadNotifications = 0;

            if (notificationResponse != null
                    && notificationResponse.getData() != null) {

                unreadNotifications = notificationResponse.getData();
            }

            model.addAttribute("leaveBalance", leaveBalance);
            model.addAttribute("pendingLeaves", pendingLeaves);
            model.addAttribute("performanceReviews", performanceReviews);
            model.addAttribute("unreadNotifications", unreadNotifications);

        } catch (Exception e) {

            e.printStackTrace();

            // Keep dashboard usable even if one backend call fails.
            model.addAttribute("leaveBalance", 0);
            model.addAttribute("pendingLeaves", 0);
            model.addAttribute("performanceReviews", 0);
            model.addAttribute("unreadNotifications", 0);
        }

        return "employee/dashboard";
    }

    @GetMapping("/manager/dashboard")
    public String managerDashboard() {
        return "manager/dashboard";
    }
}