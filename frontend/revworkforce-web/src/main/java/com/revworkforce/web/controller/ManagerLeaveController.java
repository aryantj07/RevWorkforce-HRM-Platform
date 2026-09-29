package com.revworkforce.web.controller;

import com.revworkforce.web.model.ApiResponse;
import com.revworkforce.web.model.LeaveActionRequest;
import com.revworkforce.web.model.LeaveResponse;
import com.revworkforce.web.service.GatewayClient;

import jakarta.servlet.http.HttpSession;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ManagerLeaveController {

    private final GatewayClient gatewayClient;

    public ManagerLeaveController(GatewayClient gatewayClient) {
        this.gatewayClient = gatewayClient;
    }

    @GetMapping("/manager/leave-approvals")
    public String leaveApprovals(
            HttpSession session,
            Model model) {

        if (session.getAttribute("token") == null) {
            return "redirect:/login";
        }

        try {

            ApiResponse<List<LeaveResponse>> response =
                    gatewayClient
                            .authenticatedGet(
                                    "/leave-service/api/v1/leave-requests/status/PENDING",
                                    session)
                            .retrieve()
                            .body(
                                    new ParameterizedTypeReference<
                                            ApiResponse<List<LeaveResponse>>>() {
                                    }
                            );

            List<LeaveResponse> leaves =
                    response != null && response.getData() != null
                            ? response.getData()
                            : List.of();

            model.addAttribute("leaves", leaves);

        } catch (Exception e) {

            e.printStackTrace();

            model.addAttribute(
                    "error",
                    "Unable to load pending leave requests."
            );

            model.addAttribute("leaves", List.of());
        }

        return "manager/leave-approvals";
    }

    @PostMapping("/manager/leave-approvals/{id}/approve")
    public String approveLeave(
            @PathVariable Long id,
            @RequestParam(required = false) String comments,
            HttpSession session) {

        if (session.getAttribute("token") == null) {
            return "redirect:/login";
        }

        try {

            Long approverId =
                    (Long) session.getAttribute("userId");

            LeaveActionRequest request =
                    new LeaveActionRequest();

            request.setApproverId(approverId);
            request.setComments(comments);

            gatewayClient
                    .authenticatedPatch(
                            "/leave-service/api/v1/leave-requests/"
                                    + id
                                    + "/approve",
                            session)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(request)
                    .retrieve()
                    .toBodilessEntity();

            return "redirect:/manager/leave-approvals?success=Leave approved successfully";

        } catch (Exception e) {

            e.printStackTrace();

            return "redirect:/manager/leave-approvals?error=Unable to approve leave";
        }
    }

    @PostMapping("/manager/leave-approvals/{id}/reject")
    public String rejectLeave(
            @PathVariable Long id,
            @RequestParam(required = false) String comments,
            HttpSession session) {

        if (session.getAttribute("token") == null) {
            return "redirect:/login";
        }

        try {

            Long approverId =
                    (Long) session.getAttribute("userId");

            LeaveActionRequest request =
                    new LeaveActionRequest();

            request.setApproverId(approverId);
            request.setComments(comments);

            gatewayClient
                    .authenticatedPatch(
                            "/leave-service/api/v1/leave-requests/"
                                    + id
                                    + "/reject",
                            session)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(request)
                    .retrieve()
                    .toBodilessEntity();

            return "redirect:/manager/leave-approvals?success=Leave rejected successfully";

        } catch (Exception e) {

            e.printStackTrace();

            return "redirect:/manager/leave-approvals?error=Unable to reject leave";
        }
    }
}