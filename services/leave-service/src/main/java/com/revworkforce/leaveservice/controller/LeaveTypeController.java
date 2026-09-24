package com.revworkforce.leaveservice.controller;

import com.revworkforce.leaveservice.dto.request.LeaveTypeCreateDto;
import com.revworkforce.leaveservice.dto.response.ApiResponse;
import com.revworkforce.leaveservice.dto.response.LeaveTypeResponseDto;
import com.revworkforce.leaveservice.service.LeaveTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/leave-types")
@Tag(name = "Leave Types", description = "Endpoints for managing company leave types")
public class LeaveTypeController {

    private final LeaveTypeService leaveTypeService;

    public LeaveTypeController(LeaveTypeService leaveTypeService) {
        this.leaveTypeService = leaveTypeService;
    }

    @PostMapping
    @Operation(summary = "Create a new leave type")
    public ResponseEntity<ApiResponse<LeaveTypeResponseDto>> createLeaveType(@Valid @RequestBody LeaveTypeCreateDto requestDto) {
        LeaveTypeResponseDto created = leaveTypeService.createLeaveType(requestDto);
        return new ResponseEntity<>(ApiResponse.success("Leave type created successfully", created), HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Get all leave types")
    public ResponseEntity<ApiResponse<List<LeaveTypeResponseDto>>> getAllLeaveTypes() {
        return ResponseEntity.ok(ApiResponse.success(leaveTypeService.getAllLeaveTypes()));
    }

    @GetMapping("/active")
    @Operation(summary = "Get all active leave types")
    public ResponseEntity<ApiResponse<List<LeaveTypeResponseDto>>> getActiveLeaveTypes() {
        return ResponseEntity.ok(ApiResponse.success(leaveTypeService.getActiveLeaveTypes()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get leave type by ID")
    public ResponseEntity<ApiResponse<LeaveTypeResponseDto>> getLeaveTypeById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(leaveTypeService.getLeaveTypeById(id)));
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Activate or deactivate a leave type")
    public ResponseEntity<ApiResponse<LeaveTypeResponseDto>> updateStatus(@PathVariable Long id, @RequestParam boolean active) {
        return ResponseEntity.ok(ApiResponse.success("Leave type status updated", leaveTypeService.updateLeaveTypeStatus(id, active)));
    }
}
