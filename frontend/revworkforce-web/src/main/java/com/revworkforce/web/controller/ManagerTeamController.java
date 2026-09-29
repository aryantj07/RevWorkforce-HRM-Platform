package com.revworkforce.web.controller;

import com.revworkforce.web.model.Employee;
import com.revworkforce.web.service.GatewayClient;

import jakarta.servlet.http.HttpSession;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ManagerTeamController {

    private final GatewayClient gatewayClient;

    public ManagerTeamController(GatewayClient gatewayClient) {
        this.gatewayClient = gatewayClient;
    }

    @GetMapping("/manager/team")
    public String teamEmployees(
            HttpSession session,
            Model model) {

        if (session.getAttribute("token") == null) {
            return "redirect:/login";
        }

        try {

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
                    "employees",
                    employees != null ? employees : List.of()
            );

        } catch (Exception e) {

            e.printStackTrace();

            model.addAttribute(
                    "error",
                    "Unable to load employees."
            );

            model.addAttribute(
                    "employees",
                    List.of()
            );
        }

        return "manager/team";
    }
}