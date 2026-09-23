package com.revworkforce.reporting_service.client;

import com.revworkforce.reporting_service.dto.PerformanceSummaryResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "performance-service")
public interface PerformanceClient {

    @GetMapping("/api/performance/report/summary")
    PerformanceSummaryResponse getPerformanceSummary();
}