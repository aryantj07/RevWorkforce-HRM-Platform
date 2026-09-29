package com.revworkforce.web.controller;

import com.revworkforce.web.model.UserProfile;
import com.revworkforce.web.service.GatewayClient;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestClientResponseException;

@Controller
public class ProfileController {

    private final GatewayClient gatewayClient;

    public ProfileController(GatewayClient gatewayClient) {
        this.gatewayClient = gatewayClient;
    }

    @GetMapping("/employee/profile")
    public String employeeProfile(HttpSession session, Model model) {
        try {

            System.out.println("Calling Gateway: GET /api/users/profile");

            UserProfile profile = gatewayClient
                    .authenticatedGet("/api/users/profile", session)
                    .retrieve()
                    .body(UserProfile.class);

            System.out.println("Profile response received: " + profile);

            model.addAttribute("profile", profile);

            System.out.println("Profile loaded successfully.");

            return "employee/profile";

        } catch (RestClientResponseException e) {

            System.out.println("========== PROFILE API ERROR ==========");
            System.out.println("HTTP Status: " + e.getStatusCode());
            System.out.println("Response Body: " + e.getResponseBodyAsString());
            e.printStackTrace();

            model.addAttribute(
                    "error",
                    "Profile API error: " + e.getStatusCode()
            );

            return "employee/profile";

        } catch (Exception e) {

            System.out.println("========== PROFILE UNKNOWN ERROR ==========");
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();

            model.addAttribute(
                    "error",
                    "Profile error: " + e.getMessage()
            );

            return "employee/profile";
        }
    }
}