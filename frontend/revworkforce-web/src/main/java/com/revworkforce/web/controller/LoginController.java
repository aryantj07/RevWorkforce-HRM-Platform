package com.revworkforce.web.controller;

import com.revworkforce.web.model.LoginRequest;
import com.revworkforce.web.model.LoginResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestClient;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {

    private final RestClient restClient;

    public LoginController(
            RestClient.Builder restClientBuilder,
            @Value("${gateway.base-url}") String gatewayUrl) {

        this.restClient = restClientBuilder
                .baseUrl(gatewayUrl)
                .build();
    }

    @PostMapping("/login")
    public String login(
            @RequestParam String username,
            @RequestParam String password,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        try {

            LoginRequest request =
                    new LoginRequest(username, password);

            System.out.println("LOGIN REQUEST: username=" + username);
            LoginResponse response = restClient
                    .post()
                    .uri("/api/users/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(request)
                    .retrieve()
                    .body(LoginResponse.class);

            if (response == null) {
                redirectAttributes.addFlashAttribute(
                        "error", "Invalid login response.");
                return "redirect:/login";
            }

            // Store login information in the frontend session
            session.setAttribute("token", response.getToken());
            session.setAttribute("userId", response.getUserId());
            session.setAttribute("username", response.getUsername());
            session.setAttribute("role", response.getRole());

            // Redirect according to role
            if ("ADMIN".equalsIgnoreCase(response.getRole())) {
                return "redirect:/admin/dashboard";
            }

            if ("MANAGER".equalsIgnoreCase(response.getRole())) {
                return "redirect:/manager/dashboard";
            }

            return "redirect:/employee/dashboard";

        } catch (Exception e) {

            e.printStackTrace();

            redirectAttributes.addFlashAttribute(
                    "error", "Login error: " + e.getMessage());

            return "redirect:/login";
        }
    }
}