package com.revworkforce.employee_management_service.controller;

import com.revworkforce.employee_management_service.dto.AnnouncementDto;
import com.revworkforce.employee_management_service.service.AnnouncementService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/announcements")
public class AnnouncementController {

    private final AnnouncementService announcementService;

    public AnnouncementController(AnnouncementService announcementService) {
        this.announcementService = announcementService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AnnouncementDto createAnnouncement(
            @Valid @RequestBody AnnouncementDto dto) {

        return announcementService.createAnnouncement(dto);
    }

    @GetMapping
    public List<AnnouncementDto> getAllAnnouncements() {

        return announcementService.getAllAnnouncements();
    }

    @GetMapping("/{id}")
    public AnnouncementDto getAnnouncementById(
            @PathVariable Long id) {

        return announcementService.getAnnouncementById(id);
    }

    @PutMapping("/{id}")
    public AnnouncementDto updateAnnouncement(
            @PathVariable Long id,
            @Valid @RequestBody AnnouncementDto dto) {

        return announcementService.updateAnnouncement(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAnnouncement(@PathVariable Long id) {

        announcementService.deleteAnnouncement(id);
    }
}