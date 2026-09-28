package com.revworkforce.performance_service.client;

import com.revworkforce.performance_service.dto.NotificationCreateDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "notification-service")
public interface NotificationClient {

    @PostMapping("/api/v1/notifications")
    void createNotification(@RequestBody NotificationCreateDto notification);
}