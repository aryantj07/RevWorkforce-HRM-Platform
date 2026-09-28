package com.revworkforce.leaveservice.client;

import com.revworkforce.leaveservice.dto.request.NotificationCreateDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.revworkforce.leaveservice.dto.request.NotificationCreateDto;

@FeignClient(name = "notification-service")
public interface NotificationClient {

    @PostMapping("/api/v1/notifications")
    void createNotification(@RequestBody NotificationCreateDto notification);
}