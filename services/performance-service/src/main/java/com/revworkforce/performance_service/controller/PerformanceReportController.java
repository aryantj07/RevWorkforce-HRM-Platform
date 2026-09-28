package com.revworkforce.performance_service.controller;

import com.revworkforce.performance_service.dto.PerformanceSummaryResponse;
import com.revworkforce.performance_service.service.PerformanceReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/performance/report")
public class PerformanceReportController {

    private final PerformanceReviewService performanceReviewService;

    public PerformanceReportController(
            PerformanceReviewService performanceReviewService) {

        this.performanceReviewService = performanceReviewService;
    }

    @GetMapping("/summary")
    public ResponseEntity<PerformanceSummaryResponse> getPerformanceSummary() {

        PerformanceSummaryResponse response =
                performanceReviewService.getPerformanceSummary();

        return ResponseEntity.ok(response);
    }
}