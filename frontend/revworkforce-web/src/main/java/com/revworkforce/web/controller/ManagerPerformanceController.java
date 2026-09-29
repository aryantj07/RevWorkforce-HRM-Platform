package com.revworkforce.web.controller;

import com.revworkforce.web.model.ApiResponse;
import com.revworkforce.web.model.Employee;
import com.revworkforce.web.model.FeedbackRequest;
import com.revworkforce.web.model.PerformanceReview;
import com.revworkforce.web.model.PerformanceReviewRequest;
import com.revworkforce.web.service.GatewayClient;
import com.revworkforce.web.model.RatingRequest;

import jakarta.servlet.http.HttpSession;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ManagerPerformanceController {

    private final GatewayClient gatewayClient;

    public ManagerPerformanceController(GatewayClient gatewayClient) {
        this.gatewayClient = gatewayClient;
    }

    @GetMapping("/manager/performance")
    public String performance(
            HttpSession session,
            Model model) {

        if (session.getAttribute("token") == null) {
            return "redirect:/login";
        }

        try {

            List<PerformanceReview> reviews =
                    gatewayClient
                            .authenticatedGet(
                                    "/performance-service/performance-reviews",
                                    session)
                            .retrieve()
                            .body(
                                    new ParameterizedTypeReference<
                                            List<PerformanceReview>>() {
                                    }
                            );

            List<Employee> employees =
                    gatewayClient
                            .authenticatedGet(
                                    "/employee-management-service/api/employees",
                                    session)
                            .retrieve()
                            .body(
                                    new ParameterizedTypeReference<
                                            List<Employee>>() {
                                    }
                            );

            model.addAttribute(
                    "reviews",
                    reviews != null ? reviews : List.of()
            );

            model.addAttribute(
                    "employees",
                    employees != null ? employees : List.of()
            );

        } catch (Exception e) {

            e.printStackTrace();

            model.addAttribute(
                    "error",
                    "Unable to load performance data."
            );

            model.addAttribute("reviews", List.of());
            model.addAttribute("employees", List.of());
        }

        return "manager/performance";
    }

    @PostMapping("/manager/performance/create")
    public String createReview(
            @RequestParam Long employeeId,
            @RequestParam String reviewPeriod,
            @RequestParam String selfReview,
            HttpSession session) {

        if (session.getAttribute("token") == null) {
            return "redirect:/login";
        }

        try {

            Long managerId =
                    (Long) session.getAttribute("userId");

            PerformanceReviewRequest request =
                    new PerformanceReviewRequest();

            request.setEmployeeId(employeeId);
            request.setManagerId(managerId);
            request.setReviewPeriod(reviewPeriod);
            request.setSelfReview(selfReview);

            gatewayClient
                    .authenticatedPost(
                            "/performance-service/performance-reviews",
                            session)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(request)
                    .retrieve()
                    .toBodilessEntity();

            return "redirect:/manager/performance?success=Performance review created successfully";

        } catch (Exception e) {

            e.printStackTrace();

            return "redirect:/manager/performance?error=Unable to create performance review";
        }
    }

    @PostMapping("/manager/performance/{id}/feedback")
    public String submitFeedback(
            @PathVariable Long id,
            @RequestParam String feedback,
            HttpSession session) {

        if (session.getAttribute("token") == null) {
            return "redirect:/login";
        }

        try {

            FeedbackRequest request =
                    new FeedbackRequest();

            request.setFeedback(feedback);

            gatewayClient
                    .authenticatedPut(
                            "/performance-service/performance-reviews/"
                                    + id
                                    + "/feedback",
                            session)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(request)
                    .retrieve()
                    .toBodilessEntity();

            return "redirect:/manager/performance?success=Feedback submitted successfully";

        } catch (Exception e) {

            e.printStackTrace();

            return "redirect:/manager/performance?error=Unable to submit feedback";
        }
    }

    @PostMapping("/manager/performance/{id}/rating")
    public String submitRating(
            @PathVariable Long id,
            @RequestParam Integer rating,
            HttpSession session) {

        if (session.getAttribute("token") == null) {
            return "redirect:/login";
        }

        try {

            RatingRequest request = new RatingRequest();
            request.setRating(rating);

            gatewayClient
                    .authenticatedPut(
                            "/performance-service/performance-reviews/"
                                    + id
                                    + "/rating",
                            session)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(request)
                    .retrieve()
                    .toBodilessEntity();

            return "redirect:/manager/performance?success=Rating submitted successfully";

        } catch (Exception e) {

            e.printStackTrace();

            return "redirect:/manager/performance?error=Unable to submit rating";
        }
    }

    @PostMapping("/manager/performance/{id}/complete")
    public String completeReview(
            @PathVariable Long id,
            HttpSession session) {

        if (session.getAttribute("token") == null) {
            return "redirect:/login";
        }

        try {

            gatewayClient
                    .authenticatedPut(
                            "/performance-service/performance-reviews/"
                                    + id
                                    + "/complete",
                            session)
                    .retrieve()
                    .toBodilessEntity();

            return "redirect:/manager/performance?success=Performance review completed successfully";

        } catch (Exception e) {

            e.printStackTrace();

            return "redirect:/manager/performance?error=Unable to complete performance review";
        }
    }
}