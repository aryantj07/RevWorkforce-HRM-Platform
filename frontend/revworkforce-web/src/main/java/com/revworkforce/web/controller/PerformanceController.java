package com.revworkforce.web.controller;

import com.revworkforce.web.model.Employee;
import com.revworkforce.web.model.Goal;
import com.revworkforce.web.model.GoalRequest;
import com.revworkforce.web.model.GoalUpdateRequest;
import com.revworkforce.web.model.PerformanceReview;
import com.revworkforce.web.model.SelfReviewRequest;
import com.revworkforce.web.service.GatewayClient;

import jakarta.servlet.http.HttpSession;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class PerformanceController {

    private final GatewayClient gatewayClient;

    public PerformanceController(GatewayClient gatewayClient) {
        this.gatewayClient = gatewayClient;
    }

    @GetMapping("/employee/performance")
    public String performancePage(
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

            Employee employee =
                    gatewayClient
                            .authenticatedGet(
                                    "/employee-management-service/api/employees/user/" + userId,
                                    session)
                            .retrieve()
                            .body(Employee.class);

            if (employee == null || employee.getId() == null) {

                model.addAttribute(
                        "error",
                        "Employee profile not found."
                );

                model.addAttribute("reviews", List.of());
                model.addAttribute("goals", List.of());

                return "employee/performance";
            }

            Long employeeId = employee.getId();

            List<PerformanceReview> reviews =
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

            List<Goal> goals =
                    gatewayClient
                            .authenticatedGet(
                                    "/performance-service/goals/employee/"
                                            + employeeId,
                                    session)
                            .retrieve()
                            .body(
                                    new ParameterizedTypeReference<
                                            List<Goal>>() {
                                    }
                            );

            model.addAttribute(
                    "reviews",
                    reviews != null ? reviews : List.of()
            );

            model.addAttribute(
                    "goals",
                    goals != null ? goals : List.of()
            );

        } catch (Exception e) {

            e.printStackTrace();

            model.addAttribute(
                    "error",
                    "Unable to load performance information."
            );

            model.addAttribute("reviews", List.of());
            model.addAttribute("goals", List.of());
        }

        return "employee/performance";
    }

    @PostMapping("/employee/performance/review/{id}")
    public String submitSelfReview(
            @PathVariable Long id,
            @RequestParam String selfReview,
            HttpSession session) {

        if (session.getAttribute("token") == null) {
            return "redirect:/login";
        }

        try {

            SelfReviewRequest request =
                    new SelfReviewRequest(selfReview);

            gatewayClient
                    .authenticatedPut(
                            "/performance-service/performance-reviews/"
                                    + id
                                    + "/self-review",
                            session)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(request)
                    .retrieve()
                    .toBodilessEntity();

            return "redirect:/employee/performance?success=Self-review submitted successfully";

        } catch (Exception e) {

            e.printStackTrace();

            return "redirect:/employee/performance?error=Unable to submit self-review";
        }
    }

    @PostMapping("/employee/performance/goals")
    public String createGoal(
            @RequestParam String title,
            @RequestParam String description,
            HttpSession session) {

        if (session.getAttribute("token") == null) {
            return "redirect:/login";
        }

        try {

            Long userId = (Long) session.getAttribute("userId");

            Employee employee =
                    gatewayClient
                            .authenticatedGet(
                                    "/employee-management-service/api/employees/user/" + userId,
                                    session)
                            .retrieve()
                            .body(Employee.class);

            if (employee == null || employee.getId() == null) {
                return "redirect:/employee/performance?error=Employee profile not found";
            }

            GoalRequest request =
                    new GoalRequest(
                            employee.getId(),
                            title,
                            description
                    );

            gatewayClient
                    .authenticatedPost(
                            "/performance-service/goals",
                            session)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(request)
                    .retrieve()
                    .toBodilessEntity();

            return "redirect:/employee/performance?success=Goal created successfully";

        } catch (Exception e) {

            e.printStackTrace();

            return "redirect:/employee/performance?error=Unable to create goal";
        }
    }

    @PostMapping("/employee/performance/goals/{id}")
    public String updateGoal(
            @PathVariable Long id,
            @RequestParam String title,
            @RequestParam String description,
            @RequestParam String status,
            HttpSession session) {

        if (session.getAttribute("token") == null) {
            return "redirect:/login";
        }

        try {

            GoalUpdateRequest request =
                    new GoalUpdateRequest(
                            title,
                            description,
                            status
                    );

            gatewayClient
                    .authenticatedPut(
                            "/performance-service/goals/" + id,
                            session)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(request)
                    .retrieve()
                    .toBodilessEntity();

            return "redirect:/employee/performance?success=Goal updated successfully";

        } catch (Exception e) {

            e.printStackTrace();

            return "redirect:/employee/performance?error=Unable to update goal";
        }
    }
}