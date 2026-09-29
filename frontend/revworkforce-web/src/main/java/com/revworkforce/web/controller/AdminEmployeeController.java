package com.revworkforce.web.controller;

import com.revworkforce.web.model.Department;
import com.revworkforce.web.model.Designation;
import com.revworkforce.web.model.EmployeeRequest;
import com.revworkforce.web.model.User;
import com.revworkforce.web.service.GatewayClient;

import jakarta.servlet.http.HttpSession;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@Controller
@RequestMapping("/admin/employees")
public class AdminEmployeeController {

    private final GatewayClient gatewayClient;

    public AdminEmployeeController(GatewayClient gatewayClient) {
        this.gatewayClient = gatewayClient;
    }

    @GetMapping
    public String employeeManagement(
            HttpSession session,
            Model model) {

        if (session.getAttribute("token") == null) {
            return "redirect:/login";
        }

        try {
            List<User> users = gatewayClient
                    .authenticatedGet("/api/users", session)
                    .retrieve()
                    .body(new ParameterizedTypeReference<List<User>>() {});

            List<Department> departments = gatewayClient
                    .authenticatedGet("/employee-management-service/api/departments", session)
                    .retrieve()
                    .body(new ParameterizedTypeReference<List<Department>>() {});

            List<Designation> designations = gatewayClient
                    .authenticatedGet("/employee-management-service/api/designations", session)
                    .retrieve()
                    .body(new ParameterizedTypeReference<List<Designation>>() {});

            model.addAttribute("users", users != null ? users : List.of());
            model.addAttribute("departments", departments != null ? departments : List.of());
            model.addAttribute("designations", designations != null ? designations : List.of());

        } catch (Exception e) {
            e.printStackTrace();

            model.addAttribute("users", List.of());
            model.addAttribute("departments", List.of());
            model.addAttribute("designations", List.of());
            model.addAttribute("error", "Unable to load employee management data.");
        }

        return "admin/employees";
    }

    @PostMapping
    public String createEmployee(
            @RequestParam Long userId,
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String email,
            @RequestParam(required = false) String phoneNumber,
            @RequestParam String status,
            @RequestParam String dateOfJoining,
            @RequestParam(required = false) String address,
            @RequestParam Long departmentId,
            @RequestParam Long designationId,
            HttpSession session,
            Model model) {

        if (session.getAttribute("token") == null) {
            return "redirect:/login";
        }

        try {
            EmployeeRequest request = new EmployeeRequest();

            request.setUserId(userId);
            request.setFirstName(firstName);
            request.setLastName(lastName);
            request.setEmail(email);
            request.setPhoneNumber(phoneNumber);
            request.setStatus(status);
            request.setDateOfJoining(java.time.LocalDate.parse(dateOfJoining));
            request.setAddress(address);
            request.setDepartmentId(departmentId);
            request.setDesignationId(designationId);

            gatewayClient
                    .authenticatedPost("/employee-management-service/api/employees", session)
                    .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                    .body(request)
                    .retrieve()
                    .toBodilessEntity();

            return "redirect:/admin/employees?success=Employee created successfully";

        } catch (HttpClientErrorException e) {

            model.addAttribute(
                    "error",
                    "Unable to create employee: " + e.getResponseBodyAsString()
            );

            return employeeManagement(session, model);

        } catch (Exception e) {

            e.printStackTrace();

            model.addAttribute(
                    "error",
                    "Unable to create employee."
            );

            return employeeManagement(session, model);
        }
    }
}