package com.revworkforce.employee_management_service.client;

import com.revworkforce.employee_management_service.dto.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "user-service")
public interface UserClient {

    @GetMapping("/api/users")
    List<UserResponse> getAllUsers();
}