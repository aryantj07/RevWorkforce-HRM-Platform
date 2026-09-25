package com.revworkforce.notificationservice.service;

import com.revworkforce.notificationservice.dto.request.NotificationCreateDto;
import com.revworkforce.notificationservice.dto.response.NotificationResponseDto;

import java.util.List;

public interface NotificationService {
    NotificationResponseDto createNotification(NotificationCreateDto requestDto);
    List<NotificationResponseDto> getUserNotifications(Long userId);
    List<NotificationResponseDto> getUnreadUserNotifications(Long userId);
    long getUnreadCount(Long userId);
    NotificationResponseDto markAsRead(Long id);
    void markAllAsRead(Long userId);
    void deleteNotification(Long id);
}
