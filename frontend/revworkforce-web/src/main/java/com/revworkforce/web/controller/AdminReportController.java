package com.revworkforce.web.controller;

import com.revworkforce.web.model.DashboardResponse;
import com.revworkforce.web.model.EmployeeReport;
import com.revworkforce.web.service.GatewayClient;
import jakarta.servlet.http.HttpSession;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin/reports")
public class AdminReportController {

    private final GatewayClient gatewayClient;

    public AdminReportController(GatewayClient gatewayClient) {
        this.gatewayClient = gatewayClient;
    }

    @GetMapping
    public String reports(
            HttpSession session,
            Model model) {

        if (session.getAttribute("token") == null) {
            return "redirect:/login";
        }

        try {

            DashboardResponse dashboard = gatewayClient
                    .authenticatedGet(
                            "/reporting-service/api/reports/dashboard",
                            session)
                    .retrieve()
                    .body(DashboardResponse.class);

            List<EmployeeReport> employees = gatewayClient
                    .authenticatedGet(
                            "/reporting-service/api/reports/employees",
                            session)
                    .retrieve()
                    .body(new ParameterizedTypeReference<List<EmployeeReport>>() {});

            model.addAttribute("dashboard", dashboard);
            model.addAttribute(
                    "employees",
                    employees != null ? employees : List.of());

        } catch (Exception e) {

            e.printStackTrace();

            model.addAttribute(
                    "error",
                    "Unable to load HR reports.");

            model.addAttribute(
                    "employees",
                    List.of());
        }

        return "admin/reports";
    }
}