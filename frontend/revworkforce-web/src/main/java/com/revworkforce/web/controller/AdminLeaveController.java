package com.revworkforce.web.controller;

import com.revworkforce.web.model.Holiday;
import com.revworkforce.web.model.HolidayRequest;
import com.revworkforce.web.model.LeaveQuotaRequest;
import com.revworkforce.web.model.LeaveType;
import com.revworkforce.web.model.LeaveTypeRequest;
import com.revworkforce.web.service.GatewayClient;
import jakarta.servlet.http.HttpSession;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/leave-config")
public class AdminLeaveController {

    private final GatewayClient gatewayClient;

    public AdminLeaveController(GatewayClient gatewayClient) {
        this.gatewayClient = gatewayClient;
    }

    @GetMapping
    public String leaveConfiguration(
            HttpSession session,
            Model model) {

        if (session.getAttribute("token") == null) {
            return "redirect:/login";
        }

        try {

            List<LeaveType> leaveTypes = gatewayClient
                    .authenticatedGet(
                            "/leave-service/api/v1/leave-types",
                            session)
                    .retrieve()
                    .body(new ParameterizedTypeReference<
                            com.revworkforce.web.model.ApiResponse<List<LeaveType>>>() {})
                    .getData();

            List<Holiday> holidays = gatewayClient
                    .authenticatedGet(
                            "/leave-service/api/v1/holidays",
                            session)
                    .retrieve()
                    .body(new ParameterizedTypeReference<
                            com.revworkforce.web.model.ApiResponse<List<Holiday>>>() {})
                    .getData();

            model.addAttribute(
                    "leaveTypes",
                    leaveTypes != null ? leaveTypes : List.of());

            model.addAttribute(
                    "holidays",
                    holidays != null ? holidays : List.of());

        } catch (Exception e) {
            model.addAttribute(
                    "error",
                    "Unable to load leave configuration.");

            model.addAttribute("leaveTypes", List.of());
            model.addAttribute("holidays", List.of());
        }

        return "admin/leave-config";
    }

    @PostMapping("/types")
    public String createLeaveType(
            @RequestParam String name,
            @RequestParam String code,
            @RequestParam(required = false) String description,
            @RequestParam(defaultValue = "true") boolean paid,
            @RequestParam(defaultValue = "true") boolean active,
            HttpSession session) {

        LeaveTypeRequest request = new LeaveTypeRequest();
        request.setName(name);
        request.setCode(code);
        request.setDescription(description);
        request.setPaid(paid);
        request.setActive(active);

        gatewayClient
                .authenticatedPost(
                        "/leave-service/api/v1/leave-types",
                        session)
                .contentType(MediaType.APPLICATION_JSON)
                .body(request)
                .retrieve()
                .toBodilessEntity();

        return "redirect:/admin/leave-config?success=Leave type created successfully";
    }

    @PostMapping("/types/{id}/status")
    public String updateLeaveTypeStatus(
            @PathVariable Long id,
            @RequestParam boolean active,
            HttpSession session) {

        gatewayClient
                .authenticatedPatch(
                        "/leave-service/api/v1/leave-types/"
                                + id
                                + "/status?active="
                                + active,
                        session)
                .retrieve()
                .toBodilessEntity();

        return "redirect:/admin/leave-config?success=Leave type status updated";
    }

    @PostMapping("/quotas")
    public String setQuota(
            @RequestParam Long leaveTypeId,
            @RequestParam Integer year,
            @RequestParam Integer totalDays,
            HttpSession session) {

        LeaveQuotaRequest request = new LeaveQuotaRequest();
        request.setLeaveTypeId(leaveTypeId);
        request.setYear(year);
        request.setTotalDays(totalDays);

        gatewayClient
                .authenticatedPost(
                        "/leave-service/api/v1/leave-balances/quota",
                        session)
                .contentType(MediaType.APPLICATION_JSON)
                .body(request)
                .retrieve()
                .toBodilessEntity();

        return "redirect:/admin/leave-config?success=Leave quota allocated successfully";
    }

    @PostMapping("/holidays")
    public String createHoliday(
            @RequestParam String name,
            @RequestParam String holidayDate,
            @RequestParam(required = false) String description,
            @RequestParam(defaultValue = "false") boolean recurring,
            HttpSession session) {

        HolidayRequest request = new HolidayRequest();
        request.setName(name);
        request.setHolidayDate(java.time.LocalDate.parse(holidayDate));
        request.setDescription(description);
        request.setRecurring(recurring);

        gatewayClient
                .authenticatedPost(
                        "/leave-service/api/v1/holidays",
                        session)
                .contentType(MediaType.APPLICATION_JSON)
                .body(request)
                .retrieve()
                .toBodilessEntity();

        return "redirect:/admin/leave-config?success=Holiday created successfully";
    }

    @PostMapping("/holidays/{id}/delete")
    public String deleteHoliday(
            @PathVariable Long id,
            HttpSession session) {

        gatewayClient
                .authenticatedDelete(
                        "/leave-service/api/v1/holidays/" + id,
                        session)
                .retrieve()
                .toBodilessEntity();

        return "redirect:/admin/leave-config?success=Holiday deleted successfully";
    }
}