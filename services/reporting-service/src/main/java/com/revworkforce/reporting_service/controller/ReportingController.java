package com.revworkforce.reporting_service.controller;

import com.revworkforce.reporting_service.dto.DashboardResponse;
import com.revworkforce.reporting_service.dto.EmployeeResponse;
import com.revworkforce.reporting_service.service.ReportingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ReportingController {

    private final ReportingService reportingService;

    public ReportingController(ReportingService reportingService) {
        this.reportingService = reportingService;
    }

    @GetMapping("/dashboard")
    public ResponseEntity<DashboardResponse> getDashboard() {

        DashboardResponse response =
                reportingService.getDashboard();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/employees")
    public ResponseEntity<List<EmployeeResponse>> getEmployeeReport() {

        List<EmployeeResponse> employees =
                reportingService.getEmployeeReport();

        return ResponseEntity.ok(employees);
    }
}