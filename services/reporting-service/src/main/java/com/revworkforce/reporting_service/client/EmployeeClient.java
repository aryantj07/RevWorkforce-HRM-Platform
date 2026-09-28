package com.revworkforce.reporting_service.client;

import com.revworkforce.reporting_service.dto.EmployeeResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "employee-management-service")
public interface EmployeeClient {

    @GetMapping("/api/employees")
    List<EmployeeResponse> getAllEmployees();

    @GetMapping("/api/employees/{id}")
    EmployeeResponse getEmployeeById(@PathVariable Long id);
}