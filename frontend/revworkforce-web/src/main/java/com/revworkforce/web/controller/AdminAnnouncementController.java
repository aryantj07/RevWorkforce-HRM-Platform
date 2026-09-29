package com.revworkforce.web.controller;

import com.revworkforce.web.model.Announcement;
import com.revworkforce.web.service.GatewayClient;
import jakarta.servlet.http.HttpSession;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/announcements")
public class AdminAnnouncementController {

    private final GatewayClient gatewayClient;

    public AdminAnnouncementController(GatewayClient gatewayClient) {
        this.gatewayClient = gatewayClient;
    }

    @GetMapping
    public String announcements(
            HttpSession session,
            Model model) {

        if (session.getAttribute("token") == null) {
            return "redirect:/login";
        }

        try {

            List<Announcement> announcements = gatewayClient
                    .authenticatedGet(
                            "/employee-management-service/api/announcements",
                            session)
                    .retrieve()
                    .body(new ParameterizedTypeReference<List<Announcement>>() {});

            model.addAttribute(
                    "announcements",
                    announcements != null ? announcements : List.of());

        } catch (Exception e) {

            model.addAttribute(
                    "error",
                    "Unable to load announcements.");

            model.addAttribute("announcements", List.of());
        }

        return "admin/announcements";
    }

    @PostMapping
    public String createAnnouncement(
            @RequestParam String title,
            @RequestParam String message,
            @RequestParam(defaultValue = "true") boolean active,
            HttpSession session) {

        Announcement announcement = new Announcement();
        announcement.setTitle(title);
        announcement.setMessage(message);
        announcement.setActive(active);

        gatewayClient
                .authenticatedPost(
                        "/employee-management-service/api/announcements",
                        session)
                .contentType(MediaType.APPLICATION_JSON)
                .body(announcement)
                .retrieve()
                .toBodilessEntity();

        return "redirect:/admin/announcements?success=Announcement created successfully";
    }

    @PostMapping("/{id}/update")
    public String updateAnnouncement(
            @PathVariable Long id,
            @RequestParam String title,
            @RequestParam String message,
            @RequestParam(defaultValue = "true") boolean active,
            HttpSession session) {

        Announcement announcement = new Announcement();
        announcement.setTitle(title);
        announcement.setMessage(message);
        announcement.setActive(active);

        gatewayClient
                .authenticatedPut(
                        "/employee-management-service/api/announcements/" + id,
                        session)
                .contentType(MediaType.APPLICATION_JSON)
                .body(announcement)
                .retrieve()
                .toBodilessEntity();

        return "redirect:/admin/announcements?success=Announcement updated successfully";
    }

    @PostMapping("/{id}/delete")
    public String deleteAnnouncement(
            @PathVariable Long id,
            HttpSession session) {

        gatewayClient
                .authenticatedDelete(
                        "/employee-management-service/api/announcements/" + id,
                        session)
                .retrieve()
                .toBodilessEntity();

        return "redirect:/admin/announcements?success=Announcement deleted successfully";
    }
}