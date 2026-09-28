package com.revworkforce.performance_service.client;

import com.revworkforce.performance_service.dto.EmployeeResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "employee-management-service")
public interface EmployeeClient {

    @GetMapping("/api/employees/{id}")
    EmployeeResponse getEmployeeById(@PathVariable Long id);
}