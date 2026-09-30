package com.revworkforce.web.controller;

import com.revworkforce.web.model.Employee;
import com.revworkforce.web.service.GatewayClient;

import jakarta.servlet.http.HttpSession;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.revworkforce.web.model.Department;
import com.revworkforce.web.model.Designation;
import java.util.Map;
import java.util.function.Function;

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

            Long userId = (Long) session.getAttribute("userId");

            if (userId == null) {
                return "redirect:/login";
            }

            // Find the logged-in manager's employee record
            Employee manager =
                    gatewayClient
                            .authenticatedGet(
                                    "/employee-management-service/api/employees/user/" + userId,
                                    session)
                            .retrieve()
                            .body(Employee.class);

            if (manager == null || manager.getDepartmentId() == null) {
                model.addAttribute("employees", List.of());
                model.addAttribute(
                        "error",
                        "Manager employee record or department not found."
                );
                return "manager/team";
            }

            Long managerDepartmentId = manager.getDepartmentId();

            // Get all employees
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

            // Keep employees belonging to the manager's department
            List<Employee> teamEmployees =
                    employees == null
                            ? List.of()
                            : employees.stream()
                            .filter(employee ->
                                    !Objects.equals(employee.getId(), manager.getId())
                                            && Objects.equals(
                                            employee.getDepartmentId(),
                                            managerDepartmentId
                                    )
                            )
                            .collect(Collectors.toList());

            model.addAttribute("employees", teamEmployees);

            List<Department> departments =
                    gatewayClient
                            .authenticatedGet(
                                    "/employee-management-service/api/departments",
                                    session)
                            .retrieve()
                            .body(new ParameterizedTypeReference<List<Department>>() {});

            List<Designation> designations =
                    gatewayClient
                            .authenticatedGet(
                                    "/employee-management-service/api/designations",
                                    session)
                            .retrieve()
                            .body(new ParameterizedTypeReference<List<Designation>>() {});

            Map<Long, String> departmentNames =
                    departments == null
                            ? Map.of()
                            : departments.stream()
                            .collect(Collectors.toMap(
                                    Department::getId,
                                    Department::getName
                            ));

            Map<Long, String> designationNames =
                    designations == null
                            ? Map.of()
                            : designations.stream()
                            .collect(Collectors.toMap(
                                    Designation::getId,
                                    Designation::getName
                            ));

            model.addAttribute("departmentNames", departmentNames);
            model.addAttribute("designationNames", designationNames);

        } catch (Exception e) {

            e.printStackTrace();

            model.addAttribute(
                    "error",
                    "Unable to load team employees."
            );

            model.addAttribute(
                    "employees",
                    List.of()
            );
        }

        return "manager/team";
    }
}