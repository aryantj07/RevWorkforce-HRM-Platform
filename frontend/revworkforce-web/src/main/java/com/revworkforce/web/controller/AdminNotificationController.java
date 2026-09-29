package com.revworkforce.web.controller;

import com.revworkforce.web.model.ApiResponse;
import com.revworkforce.web.model.Notification;
import com.revworkforce.web.service.GatewayClient;
import jakarta.servlet.http.HttpSession;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/notifications")
public class AdminNotificationController {

    private final GatewayClient gatewayClient;

    public AdminNotificationController(GatewayClient gatewayClient) {
        this.gatewayClient = gatewayClient;
    }

    @GetMapping
    public String notifications(
            HttpSession session,
            Model model) {

        if (session.getAttribute("token") == null) {
            return "redirect:/login";
        }

        Long userId = (Long) session.getAttribute("userId");

        if (userId == null) {
            return "redirect:/login";
        }

        try {

            ApiResponse<List<Notification>> response =
                    gatewayClient
                            .authenticatedGet(
                                    "/notification-service/api/v1/notifications/user/"
                                            + userId,
                                    session)
                            .retrieve()
                            .body(new ParameterizedTypeReference<
                                    ApiResponse<List<Notification>>>() {});

            model.addAttribute(
                    "notifications",
                    response != null && response.getData() != null
                            ? response.getData()
                            : List.of());

        } catch (Exception e) {

            model.addAttribute(
                    "error",
                    "Unable to load notifications.");

            model.addAttribute("notifications", List.of());
        }

        return "admin/notifications";
    }

    @PostMapping("/{id}/read")
    public String markAsRead(
            @PathVariable Long id,
            HttpSession session) {

        gatewayClient
                .authenticatedPatch(
                        "/notification-service/api/v1/notifications/"
                                + id + "/read",
                        session)
                .retrieve()
                .toBodilessEntity();

        return "redirect:/admin/notifications";
    }

    @PostMapping("/read-all")
    public String markAllAsRead(
            HttpSession session) {

        Long userId = (Long) session.getAttribute("userId");

        gatewayClient
                .authenticatedPatch(
                        "/notification-service/api/v1/notifications/user/"
                                + userId + "/read-all",
                        session)
                .retrieve()
                .toBodilessEntity();

        return "redirect:/admin/notifications";
    }

    @PostMapping("/{id}/delete")
    public String delete(
            @PathVariable Long id,
            HttpSession session) {

        gatewayClient
                .authenticatedDelete(
                        "/notification-service/api/v1/notifications/" + id,
                        session)
                .retrieve()
                .toBodilessEntity();

        return "redirect:/admin/notifications";
    }
}