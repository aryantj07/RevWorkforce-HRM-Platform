package com.revworkforce.notificationservice.service.impl;

import com.revworkforce.notificationservice.dto.request.NotificationCreateDto;
import com.revworkforce.notificationservice.dto.response.NotificationResponseDto;
import com.revworkforce.notificationservice.entity.Notification;
import com.revworkforce.notificationservice.exception.ResourceNotFoundException;
import com.revworkforce.notificationservice.repository.NotificationRepository;
import com.revworkforce.notificationservice.service.NotificationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationServiceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public NotificationResponseDto createNotification(NotificationCreateDto requestDto) {
        Notification notification = new Notification(
                requestDto.getUserId(),
                requestDto.getType(),
                requestDto.getTitle().trim(),
                requestDto.getMessage().trim()
        );

        Notification saved = notificationRepository.save(notification);
        return mapToDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<NotificationResponseDto> getUserNotifications(Long userId) {
        return notificationRepository.findByUserIdOrderByCreatedAtDesc(userId).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<NotificationResponseDto> getUnreadUserNotifications(Long userId) {
        return notificationRepository.findByUserIdAndIsReadFalseOrderByCreatedAtDesc(userId).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public long getUnreadCount(Long userId) {
        return notificationRepository.countByUserIdAndIsReadFalse(userId);
    }

    @Override
    public NotificationResponseDto markAsRead(Long id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notification not found with id: " + id));

        notification.setRead(true);
        Notification updated = notificationRepository.save(notification);
        return mapToDto(updated);
    }

    @Override
    public void markAllAsRead(Long userId) {
        notificationRepository.markAllAsReadForUser(userId);
    }

    @Override
    public void deleteNotification(Long id) {
        if (!notificationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Notification not found with id: " + id);
        }
        notificationRepository.deleteById(id);
    }

    private NotificationResponseDto mapToDto(Notification entity) {
        return new NotificationResponseDto(
                entity.getId(),
                entity.getUserId(),
                entity.getType(),
                entity.getTitle(),
                entity.getMessage(),
                entity.isRead(),
                entity.getCreatedAt()
        );
    }
}
