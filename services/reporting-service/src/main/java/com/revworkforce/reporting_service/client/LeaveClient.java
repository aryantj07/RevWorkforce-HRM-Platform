package com.revworkforce.reporting_service.client;

import com.revworkforce.reporting_service.dto.LeaveSummaryResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "leave-service")
public interface LeaveClient {

    @GetMapping("/api/leaves/report/summary")
    LeaveSummaryResponse getLeaveSummary();
}