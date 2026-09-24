package com.revworkforce.leaveservice.controller;

import com.revworkforce.leaveservice.dto.request.LeaveActionRequestDto;
import com.revworkforce.leaveservice.dto.request.LeaveApplyRequestDto;
import com.revworkforce.leaveservice.dto.response.ApiResponse;
import com.revworkforce.leaveservice.dto.response.LeaveResponseDto;
import com.revworkforce.leaveservice.entity.enums.LeaveStatus;
import com.revworkforce.leaveservice.service.LeaveRequestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/leave-requests")
@Tag(name = "Leave Requests", description = "Endpoints for applying, approving, rejecting, and cancelling leaves")
public class LeaveRequestController {

    private final LeaveRequestService leaveRequestService;

    public LeaveRequestController(LeaveRequestService leaveRequestService) {
        this.leaveRequestService = leaveRequestService;
    }

    @PostMapping("/apply")
    @Operation(summary = "Apply for leave")
    public ResponseEntity<ApiResponse<LeaveResponseDto>> applyLeave(@Valid @RequestBody LeaveApplyRequestDto requestDto) {
        LeaveResponseDto response = leaveRequestService.applyLeave(requestDto);
        return new ResponseEntity<>(ApiResponse.success("Leave applied successfully", response), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}/approve")
    @Operation(summary = "Approve a pending leave request")
    public ResponseEntity<ApiResponse<LeaveResponseDto>> approveLeave(
            @PathVariable Long id,
            @Valid @RequestBody LeaveActionRequestDto actionDto) {
        LeaveResponseDto approved = leaveRequestService.approveLeave(id, actionDto);
        return ResponseEntity.ok(ApiResponse.success("Leave approved successfully", approved));
    }

    @PatchMapping("/{id}/reject")
    @Operation(summary = "Reject a pending leave request")
    public ResponseEntity<ApiResponse<LeaveResponseDto>> rejectLeave(
            @PathVariable Long id,
            @Valid @RequestBody LeaveActionRequestDto actionDto) {
        LeaveResponseDto rejected = leaveRequestService.rejectLeave(id, actionDto);
        return ResponseEntity.ok(ApiResponse.success("Leave rejected successfully", rejected));
    }

    @PatchMapping("/{id}/cancel")
    @Operation(summary = "Cancel a leave request by employee")
    public ResponseEntity<ApiResponse<LeaveResponseDto>> cancelLeave(
            @PathVariable Long id,
            @RequestParam Long employeeId) {
        LeaveResponseDto cancelled = leaveRequestService.cancelLeave(id, employeeId);
        return ResponseEntity.ok(ApiResponse.success("Leave cancelled successfully", cancelled));
    }

    @GetMapping("/employee/{employeeId}")
    @Operation(summary = "Get leave history for an employee")
    public ResponseEntity<ApiResponse<List<LeaveResponseDto>>> getEmployeeLeaveHistory(@PathVariable Long employeeId) {
        return ResponseEntity.ok(ApiResponse.success(leaveRequestService.getEmployeeLeaveHistory(employeeId)));
    }

    @GetMapping("/status/{status}")
    @Operation(summary = "Get all leaves filtered by status (e.g. PENDING, APPROVED, REJECTED, CANCELLED)")
    public ResponseEntity<ApiResponse<List<LeaveResponseDto>>> getLeavesByStatus(@PathVariable LeaveStatus status) {
        return ResponseEntity.ok(ApiResponse.success(leaveRequestService.getLeavesByStatus(status)));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get leave details by ID")
    public ResponseEntity<ApiResponse<LeaveResponseDto>> getLeaveById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(leaveRequestService.getLeaveById(id)));
    }
}
