package com.revworkforce.employee_management_service.service;

import com.revworkforce.employee_management_service.dto.AnnouncementDto;

import java.util.List;

public interface AnnouncementService {

    AnnouncementDto createAnnouncement(AnnouncementDto announcementDto);

    List<AnnouncementDto> getAllAnnouncements();

    AnnouncementDto getAnnouncementById(Long id);

    AnnouncementDto updateAnnouncement(Long id, AnnouncementDto announcementDto);

    void deleteAnnouncement(Long id);
}