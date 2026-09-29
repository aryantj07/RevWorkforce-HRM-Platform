package com.revworkforce.web.controller;

import com.revworkforce.web.model.Department;
import com.revworkforce.web.model.Designation;
import com.revworkforce.web.service.GatewayClient;
import jakarta.servlet.http.HttpSession;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminDepartmentController {

    private final GatewayClient gatewayClient;

    public AdminDepartmentController(GatewayClient gatewayClient) {
        this.gatewayClient = gatewayClient;
    }

    @GetMapping("/departments")
    public String departments(HttpSession session, Model model) {

        if (session.getAttribute("token") == null) {
            return "redirect:/login";
        }

        try {
            List<Department> departments = gatewayClient
                    .authenticatedGet(
                            "/employee-management-service/api/departments",
                            session)
                    .retrieve()
                    .body(new ParameterizedTypeReference<List<Department>>() {});

            List<Designation> designations = gatewayClient
                    .authenticatedGet(
                            "/employee-management-service/api/designations",
                            session)
                    .retrieve()
                    .body(new ParameterizedTypeReference<List<Designation>>() {});

            model.addAttribute("departments",
                    departments != null ? departments : List.of());

            model.addAttribute("designations",
                    designations != null ? designations : List.of());

        } catch (Exception e) {
            model.addAttribute("error",
                    "Unable to load departments and designations.");
            model.addAttribute("departments", List.of());
            model.addAttribute("designations", List.of());
        }

        return "admin/departments";
    }

    @PostMapping("/departments")
    public String createDepartment(
            @RequestParam String name,
            @RequestParam(required = false) String description,
            HttpSession session) {

        Department department = new Department();
        department.setName(name);
        department.setDescription(description);

        gatewayClient
                .authenticatedPost(
                        "/employee-management-service/api/departments",
                        session)
                .contentType(MediaType.APPLICATION_JSON)
                .body(department)
                .retrieve()
                .toBodilessEntity();

        return "redirect:/admin/departments?success=Department created successfully";
    }

    @PostMapping("/departments/{id}/update")
    public String updateDepartment(
            @PathVariable Long id,
            @RequestParam String name,
            @RequestParam(required = false) String description,
            HttpSession session) {

        Department department = new Department();
        department.setName(name);
        department.setDescription(description);

        gatewayClient
                .authenticatedPut(
                        "/employee-management-service/api/departments/" + id,
                        session)
                .contentType(MediaType.APPLICATION_JSON)
                .body(department)
                .retrieve()
                .toBodilessEntity();

        return "redirect:/admin/departments?success=Department updated successfully";
    }

    @PostMapping("/departments/{id}/delete")
    public String deleteDepartment(
            @PathVariable Long id,
            HttpSession session) {

        gatewayClient
                .authenticatedDelete(
                        "/employee-management-service/api/departments/" + id,
                        session)
                .retrieve()
                .toBodilessEntity();

        return "redirect:/admin/departments?success=Department deleted successfully";
    }

    @PostMapping("/designations")
    public String createDesignation(
            @RequestParam String name,
            @RequestParam(required = false) String description,
            HttpSession session) {

        Designation designation = new Designation();
        designation.setName(name);
        designation.setDescription(description);

        gatewayClient
                .authenticatedPost(
                        "/employee-management-service/api/designations",
                        session)
                .contentType(MediaType.APPLICATION_JSON)
                .body(designation)
                .retrieve()
                .toBodilessEntity();

        return "redirect:/admin/departments?success=Designation created successfully";
    }

    @PostMapping("/designations/{id}/update")
    public String updateDesignation(
            @PathVariable Long id,
            @RequestParam String name,
            @RequestParam(required = false) String description,
            HttpSession session) {

        Designation designation = new Designation();
        designation.setName(name);
        designation.setDescription(description);

        gatewayClient
                .authenticatedPut(
                        "/employee-management-service/api/designations/" + id,
                        session)
                .contentType(MediaType.APPLICATION_JSON)
                .body(designation)
                .retrieve()
                .toBodilessEntity();

        return "redirect:/admin/departments?success=Designation updated successfully";
    }

    @PostMapping("/designations/{id}/delete")
    public String deleteDesignation(
            @PathVariable Long id,
            HttpSession session) {

        gatewayClient
                .authenticatedDelete(
                        "/employee-management-service/api/designations/" + id,
                        session)
                .retrieve()
                .toBodilessEntity();

        return "redirect:/admin/departments?success=Designation deleted successfully";
    }
}