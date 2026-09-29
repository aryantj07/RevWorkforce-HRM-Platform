package com.revworkforce.web.controller;

import com.revworkforce.web.model.ApiResponse;
import com.revworkforce.web.model.LeaveApplyRequest;
import com.revworkforce.web.model.LeaveType;
import com.revworkforce.web.service.GatewayClient;
import com.revworkforce.web.model.Employee;
import com.revworkforce.web.model.LeaveResponse;
import com.revworkforce.web.model.LeaveBalance;

import jakarta.servlet.http.HttpSession;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
public class LeaveController {

    private final GatewayClient gatewayClient;

    public LeaveController(GatewayClient gatewayClient) {
        this.gatewayClient = gatewayClient;
    }

    @GetMapping("/employee/leave/apply")
    public String applyLeavePage(
            HttpSession session,
            Model model) {

        if (session.getAttribute("token") == null) {
            return "redirect:/login";
        }

        try {

            ApiResponse<List<LeaveType>> response =
                    gatewayClient
                            .authenticatedGet(
                                    "/leave-service/api/v1/leave-types/active",
                                    session)
                            .retrieve()
                            .body(
                                    new ParameterizedTypeReference<ApiResponse<List<LeaveType>>>() {
                                    });

            if (response != null && response.getData() != null) {
                model.addAttribute("leaveTypes", response.getData());
            } else {
                model.addAttribute("leaveTypes", List.of());
            }

        } catch (Exception e) {

            e.printStackTrace();

            model.addAttribute("leaveTypes", List.of());
            model.addAttribute(
                    "error",
                    "Unable to load leave types."
            );
        }

        return "employee/apply-leave";
    }


    @PostMapping("/employee/leave/apply")
    public String applyLeave(
            @RequestParam Long leaveTypeId,
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate,
            @RequestParam String reason,
            HttpSession session,
            Model model) {

        if (session.getAttribute("token") == null) {
            return "redirect:/login";
        }

        try {

            Long userId = (Long) session.getAttribute("userId");

            if (userId == null) {
                model.addAttribute(
                        "error",
                        "User session expired. Please login again."
                );

                return loadLeavePage(session, model);
            }

            /*
             * Resolve the logged-in user's Employee ID.
             */
            Employee employee =
                    gatewayClient
                            .authenticatedGet(
                                    "/employee-management-service/api/employees/user/" + userId,
                                    session)
                            .retrieve()
                            .body(Employee.class);

            if (employee == null || employee.getId() == null) {

                model.addAttribute(
                        "error",
                        "Employee profile not found. Please contact an administrator."
                );

                return loadLeavePage(session, model);
            }

            LeaveApplyRequest request = new LeaveApplyRequest();

            request.setEmployeeId(employee.getId());

            request.setLeaveTypeId(leaveTypeId);
            request.setStartDate(startDate);
            request.setEndDate(endDate);
            request.setReason(reason);

            gatewayClient
                    .authenticatedPost(
                            "/leave-service/api/v1/leave-requests/apply",
                            session)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(request)
                    .retrieve()
                    .toBodilessEntity();

            return "redirect:/employee/leave/apply?success=Leave applied successfully";

        } catch (Exception e) {

            e.printStackTrace();

            model.addAttribute(
                    "error",
                    extractErrorMessage(e)
            );

            return loadLeavePage(session, model);
        }
    }

    @GetMapping("/employee/leave/history")
    public String leaveHistory(
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

            Employee employee =
                    gatewayClient
                            .authenticatedGet(
                                    "/employee-management-service/api/employees/user/" + userId,
                                    session)
                            .retrieve()
                            .body(Employee.class);

            if (employee == null || employee.getId() == null) {

                model.addAttribute(
                        "error",
                        "Employee profile not found."
                );

                model.addAttribute("leaves", List.of());

                return "employee/leave-history";
            }

            ApiResponse<List<LeaveResponse>> response =
                    gatewayClient
                            .authenticatedGet(
                                    "/leave-service/api/v1/leave-requests/employee/"
                                            + employee.getId(),
                                    session)
                            .retrieve()
                            .body(
                                    new ParameterizedTypeReference<
                                            ApiResponse<List<LeaveResponse>>>() {
                                    }
                            );

            if (response != null && response.getData() != null) {

                model.addAttribute(
                        "leaves",
                        response.getData()
                );

            } else {

                model.addAttribute(
                        "leaves",
                        List.of()
                );
            }

        } catch (Exception e) {

            e.printStackTrace();

            model.addAttribute(
                    "error",
                    "Unable to load leave history."
            );

            model.addAttribute(
                    "leaves",
                    List.of()
            );
        }

        return "employee/leave-history";
    }

    @GetMapping("/employee/leave/balance")
    public String leaveBalance(
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

            Employee employee =
                    gatewayClient
                            .authenticatedGet(
                                    "/employee-management-service/api/employees/user/" + userId,
                                    session)
                            .retrieve()
                            .body(Employee.class);

            if (employee == null || employee.getId() == null) {

                model.addAttribute(
                        "error",
                        "Employee profile not found."
                );

                model.addAttribute("balances", List.of());

                return "employee/leave-balance";
            }

            ApiResponse<List<LeaveBalance>> response =
                    gatewayClient
                            .authenticatedGet(
                                    "/leave-service/api/v1/leave-balances/employee/"
                                            + employee.getId(),
                                    session)
                            .retrieve()
                            .body(
                                    new ParameterizedTypeReference<
                                            ApiResponse<List<LeaveBalance>>>() {
                                    }
                            );

            if (response != null && response.getData() != null) {

                model.addAttribute(
                        "balances",
                        response.getData()
                );

            } else {

                model.addAttribute(
                        "balances",
                        List.of()
                );
            }

        } catch (Exception e) {

            e.printStackTrace();

            model.addAttribute(
                    "error",
                    "Unable to load your leave balance."
            );

            model.addAttribute(
                    "balances",
                    List.of()
            );
        }

        return "employee/leave-balance";
    }

    private String loadLeavePage(
            HttpSession session,
            Model model) {

        try {

            ApiResponse<List<LeaveType>> response =
                    gatewayClient
                            .authenticatedGet(
                                    "/leave-service/api/v1/leave-types/active",
                                    session)
                            .retrieve()
                            .body(
                                    new ParameterizedTypeReference<ApiResponse<List<LeaveType>>>() {
                                    });

            if (response != null && response.getData() != null) {
                model.addAttribute("leaveTypes", response.getData());
            } else {
                model.addAttribute("leaveTypes", List.of());
            }

        } catch (Exception e) {

            model.addAttribute("leaveTypes", List.of());
        }

        return "employee/apply-leave";
    }


    private String extractErrorMessage(Exception e) {

        if (e.getMessage() == null) {
            return "Unable to apply leave.";
        }

        return "Unable to apply leave: " + e.getMessage();
    }
}