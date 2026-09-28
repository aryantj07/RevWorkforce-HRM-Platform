package com.revworkforce.employee_management_service.client;

import com.revworkforce.employee_management_service.dto.NotificationCreateDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "notification-service")
public interface NotificationClient {

    @PostMapping("/api/v1/notifications")
    void createNotification(@RequestBody NotificationCreateDto notification);
}