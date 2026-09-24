package com.revworkforce.leaveservice.controller;

import com.revworkforce.leaveservice.dto.request.LeaveQuotaSetDto;
import com.revworkforce.leaveservice.dto.response.ApiResponse;
import com.revworkforce.leaveservice.dto.response.LeaveBalanceResponseDto;
import com.revworkforce.leaveservice.service.LeaveBalanceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Year;
import java.util.List;

@RestController
@RequestMapping("/api/v1/leave-balances")
@Tag(name = "Leave Balances", description = "Endpoints for checking and allocating employee leave balances & quotas")
public class LeaveBalanceController {

    private final LeaveBalanceService leaveBalanceService;

    public LeaveBalanceController(LeaveBalanceService leaveBalanceService) {
        this.leaveBalanceService = leaveBalanceService;
    }

    @PostMapping("/quota")
    @Operation(summary = "Set yearly quota for a leave type")
    public ResponseEntity<ApiResponse<Void>> setYearlyQuota(@Valid @RequestBody LeaveQuotaSetDto requestDto) {
        leaveBalanceService.allocateYearlyQuota(requestDto);
        return ResponseEntity.ok(ApiResponse.success("Leave quota allocated successfully", null));
    }

    @GetMapping("/employee/{employeeId}")
    @Operation(summary = "Get all leave balances for an employee in a given year")
    public ResponseEntity<ApiResponse<List<LeaveBalanceResponseDto>>> getEmployeeBalances(
            @PathVariable Long employeeId,
            @RequestParam(required = false) Integer year) {
        int targetYear = (year != null) ? year : Year.now().getValue();
        return ResponseEntity.ok(ApiResponse.success(leaveBalanceService.getEmployeeBalances(employeeId, targetYear)));
    }

    @GetMapping("/employee/{employeeId}/type/{leaveTypeId}")
    @Operation(summary = "Get specific leave balance for an employee by leave type and year")
    public ResponseEntity<ApiResponse<LeaveBalanceResponseDto>> getEmployeeBalanceForType(
            @PathVariable Long employeeId,
            @PathVariable Long leaveTypeId,
            @RequestParam(required = false) Integer year) {
        int targetYear = (year != null) ? year : Year.now().getValue();
        return ResponseEntity.ok(ApiResponse.success(leaveBalanceService.getEmployeeBalanceForType(employeeId, leaveTypeId, targetYear)));
    }
}
