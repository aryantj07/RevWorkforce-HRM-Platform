package com.revworkforce.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import jakarta.servlet.http.HttpSession;

@Controller
public class DashboardController {

    @GetMapping("/employee/dashboard")
    public String employeeDashboard(HttpSession session) {
        return "employee/dashboard";
    }

    @GetMapping("/manager/dashboard")
    public String managerDashboard() {
        return "manager/dashboard";
    }

}