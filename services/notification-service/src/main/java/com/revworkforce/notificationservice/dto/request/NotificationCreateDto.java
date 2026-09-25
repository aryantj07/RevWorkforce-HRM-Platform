package com.revworkforce.notificationservice.dto.request;

import com.revworkforce.notificationservice.entity.enums.NotificationType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Positive;

public class NotificationCreateDto {

    @NotNull(message = "User ID is required")
    @Positive(message = "User ID must be positive")
    private Long userId;

    @NotNull(message = "Notification type is required")
    private NotificationType type;

    @NotBlank(message = "Title is required")
    @Size(min = 2, max = 150, message = "Title must be between 2 and 150 characters")
    private String title;

    @NotBlank(message = "Message is required")
    @Size(min = 2, max = 1000, message = "Message must be between 2 and 1000 characters")
    private String message;

    public NotificationCreateDto() {
    }

    public NotificationCreateDto(Long userId, NotificationType type, String title, String message) {
        this.userId = userId;
        this.type = type;
        this.title = title;
        this.message = message;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public NotificationType getType() {
        return type;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
