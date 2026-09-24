package com.revworkforce.leaveservice.controller;

import com.revworkforce.leaveservice.dto.response.LeaveSummaryResponseDto;
import com.revworkforce.leaveservice.service.LeaveReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/leaves/report")
@Tag(
        name = "Leave Reports",
        description = "Endpoints for leave reporting and workforce metrics"
)
public class LeaveReportController {

    private final LeaveReportService leaveReportService;

    public LeaveReportController(LeaveReportService leaveReportService) {
        this.leaveReportService = leaveReportService;
    }

    @GetMapping("/summary")
    @Operation(summary = "Get leave application summary")
    public ResponseEntity<LeaveSummaryResponseDto> getLeaveSummary() {
        return ResponseEntity.ok(leaveReportService.getLeaveSummary());
    }
}