package com.revworkforce.notificationservice.service;

import com.revworkforce.notificationservice.dto.request.NotificationCreateDto;
import com.revworkforce.notificationservice.dto.response.NotificationResponseDto;
import com.revworkforce.notificationservice.entity.Notification;
import com.revworkforce.notificationservice.entity.enums.NotificationType;
import com.revworkforce.notificationservice.repository.NotificationRepository;
import com.revworkforce.notificationservice.service.impl.NotificationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificationServiceTest {

    @Mock
    private NotificationRepository notificationRepository;

    @InjectMocks
    private NotificationServiceImpl notificationService;

    private Notification sampleNotification;

    @BeforeEach
    void setUp() {
        sampleNotification = new Notification(100L, NotificationType.LEAVE_APPROVED, "Leave Approved", "Your leave application #1 was approved.");
        sampleNotification.setId(1L);
    }

    @Test
    void createNotification_successful() {
        NotificationCreateDto requestDto = new NotificationCreateDto(100L, NotificationType.LEAVE_APPROVED, "Leave Approved", "Your leave application #1 was approved.");

        when(notificationRepository.save(any(Notification.class))).thenReturn(sampleNotification);

        NotificationResponseDto result = notificationService.createNotification(requestDto);

        assertNotNull(result);
        assertEquals(100L, result.getUserId());
        assertEquals("Leave Approved", result.getTitle());
        assertEquals(NotificationType.LEAVE_APPROVED, result.getType());
        assertFalse(result.isRead());
    }

    @Test
    void markAsRead_successful() {
        when(notificationRepository.findById(1L)).thenReturn(Optional.of(sampleNotification));
        when(notificationRepository.save(any(Notification.class))).thenAnswer(i -> i.getArgument(0));

        NotificationResponseDto result = notificationService.markAsRead(1L);

        assertTrue(result.isRead());
        verify(notificationRepository).save(sampleNotification);
    }

    @Test
    void getUnreadCount_shouldReturnCount() {
        when(notificationRepository.countByUserIdAndIsReadFalse(100L)).thenReturn(3L);

        long count = notificationService.getUnreadCount(100L);
        assertEquals(3L, count);
    }

    @Test
    void getUserNotifications_shouldReturnList() {
        when(notificationRepository.findByUserIdOrderByCreatedAtDesc(100L)).thenReturn(List.of(sampleNotification));

        List<NotificationResponseDto> list = notificationService.getUserNotifications(100L);
        assertEquals(1, list.size());
        assertEquals(100L, list.get(0).getUserId());
    }
}
