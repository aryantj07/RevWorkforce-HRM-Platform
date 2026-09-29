package com.revworkforce.web.controller;

import com.revworkforce.web.model.RegisterRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class RegisterController {

    private final RestClient restClient;

    public RegisterController(
            RestClient.Builder restClientBuilder,
            @Value("${gateway.base-url}") String gatewayUrl) {

        this.restClient = restClientBuilder
                .baseUrl(gatewayUrl)
                .build();
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @PostMapping("/register")
    public String register(
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam(required = false) String phone,
            RedirectAttributes redirectAttributes) {

        try {
            RegisterRequest request = new RegisterRequest(
                    username,
                    email,
                    password,
                    firstName,
                    lastName,
                    phone
            );

            restClient.post()
                    .uri("/api/users/register")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(request)
                    .retrieve()
                    .toBodilessEntity();

            redirectAttributes.addFlashAttribute(
                    "success",
                    "Registration successful. You can now log in."
            );

            return "redirect:/login";

        } catch (HttpClientErrorException e) {

            String message = "Registration failed.";

            if (e.getResponseBodyAsString() != null &&
                    !e.getResponseBodyAsString().isBlank()) {
                message = e.getResponseBodyAsString();
            }

            redirectAttributes.addFlashAttribute("error", message);

            return "redirect:/register";

        } catch (Exception e) {

            redirectAttributes.addFlashAttribute(
                    "error",
                    "Unable to register right now. Please try again."
            );

            return "redirect:/register";
        }
    }
}