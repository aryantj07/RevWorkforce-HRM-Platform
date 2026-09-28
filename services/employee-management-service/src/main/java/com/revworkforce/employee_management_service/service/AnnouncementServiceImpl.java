package com.revworkforce.employee_management_service.service;

import com.revworkforce.employee_management_service.dto.AnnouncementDto;
import com.revworkforce.employee_management_service.entity.Announcement;
import com.revworkforce.employee_management_service.exception.AnnouncementNotFoundException;
import com.revworkforce.employee_management_service.repository.AnnouncementRepository;
import org.springframework.stereotype.Service;
import com.revworkforce.employee_management_service.client.NotificationClient;
import com.revworkforce.employee_management_service.client.UserClient;
import com.revworkforce.employee_management_service.dto.UserResponse;
import com.revworkforce.employee_management_service.dto.NotificationCreateDto;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AnnouncementServiceImpl implements AnnouncementService {

    private final AnnouncementRepository announcementRepository;
    private final NotificationClient notificationClient;
    private final UserClient userClient;

    public AnnouncementServiceImpl(
            AnnouncementRepository announcementRepository,
            NotificationClient notificationClient,
            UserClient userClient) {

        this.announcementRepository = announcementRepository;
        this.notificationClient = notificationClient;
        this.userClient = userClient;
    }

    @Override
    public AnnouncementDto createAnnouncement(AnnouncementDto dto) {

        Announcement announcement = new Announcement();

        announcement.setTitle(dto.getTitle());
        announcement.setMessage(dto.getMessage());
        announcement.setCreatedAt(LocalDateTime.now());
        announcement.setActive(true);

        Announcement savedAnnouncement =
                announcementRepository.save(announcement);

        sendAnnouncementNotifications(savedAnnouncement);

        return convertToDto(savedAnnouncement);
    }

    @Override
    public List<AnnouncementDto> getAllAnnouncements() {

        return announcementRepository.findAll()
                .stream()
                .map(this::convertToDto)
                .toList();
    }

    @Override
    public AnnouncementDto getAnnouncementById(Long id) {

        Announcement announcement = announcementRepository.findById(id)
                .orElseThrow(AnnouncementNotFoundException::new);

        return convertToDto(announcement);
    }

    @Override
    public AnnouncementDto updateAnnouncement(
            Long id,
            AnnouncementDto dto) {

        Announcement existingAnnouncement =
                announcementRepository.findById(id)
                        .orElseThrow(AnnouncementNotFoundException::new);

        existingAnnouncement.setTitle(dto.getTitle());
        existingAnnouncement.setMessage(dto.getMessage());
        existingAnnouncement.setActive(dto.isActive());

        Announcement updatedAnnouncement =
                announcementRepository.save(existingAnnouncement);

        return convertToDto(updatedAnnouncement);
    }

    @Override
    public void deleteAnnouncement(Long id) {

        if (!announcementRepository.existsById(id)) {
            throw new AnnouncementNotFoundException();
        }

        announcementRepository.deleteById(id);
    }

    private AnnouncementDto convertToDto(Announcement announcement) {

        AnnouncementDto dto = new AnnouncementDto();

        dto.setId(announcement.getId());
        dto.setTitle(announcement.getTitle());
        dto.setMessage(announcement.getMessage());
        dto.setCreatedAt(announcement.getCreatedAt());
        dto.setActive(announcement.isActive());

        return dto;
    }
    private void sendAnnouncementNotifications(Announcement announcement) {

        List<UserResponse> users = userClient.getAllUsers();

        for (UserResponse user : users) {

            if (!user.isActive()) {
                continue;
            }

            NotificationCreateDto notification = new NotificationCreateDto();

            notification.setUserId(user.getId());
            notification.setType("ANNOUNCEMENT");
            notification.setTitle(announcement.getTitle());
            notification.setMessage(announcement.getMessage());

            notificationClient.createNotification(notification);
        }
    }
}