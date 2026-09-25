package com.revworkforce.notificationservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.revworkforce.notificationservice.dto.request.NotificationCreateDto;
import com.revworkforce.notificationservice.dto.response.NotificationResponseDto;
import com.revworkforce.notificationservice.entity.enums.NotificationType;
import com.revworkforce.notificationservice.service.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class NotificationControllerTest {

    private MockMvc mockMvc;

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private NotificationController notificationController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(notificationController).build();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    void createNotification_shouldReturnCreated() throws Exception {
        NotificationCreateDto requestDto = new NotificationCreateDto(
                100L, NotificationType.LEAVE_APPROVED, "Leave Approved", "Your leave was approved.");

        NotificationResponseDto responseDto = new NotificationResponseDto(
                1L, 100L, NotificationType.LEAVE_APPROVED, "Leave Approved", "Your leave was approved.", false, LocalDateTime.now());

        when(notificationService.createNotification(any(NotificationCreateDto.class))).thenReturn(responseDto);

        mockMvc.perform(post("/api/v1/notifications")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.id").value(1L))
                .andExpect(jsonPath("$.data.userId").value(100L))
                .andExpect(jsonPath("$.data.type").value("LEAVE_APPROVED"));
    }

    @Test
    void getUserNotifications_shouldReturnList() throws Exception {
        NotificationResponseDto responseDto = new NotificationResponseDto(
                1L, 100L, NotificationType.ANNOUNCEMENT, "Holiday Announcement", "Office closed on Monday.", false, LocalDateTime.now());

        when(notificationService.getUserNotifications(100L)).thenReturn(List.of(responseDto));

        mockMvc.perform(get("/api/v1/notifications/user/100"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data[0].userId").value(100L));
    }

    @Test
    void markAsRead_shouldReturnUpdated() throws Exception {
        NotificationResponseDto responseDto = new NotificationResponseDto(
                1L, 100L, NotificationType.LEAVE_APPROVED, "Leave Approved", "Your leave was approved.", true, LocalDateTime.now());

        when(notificationService.markAsRead(eq(1L))).thenReturn(responseDto);

        mockMvc.perform(patch("/api/v1/notifications/1/read"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.read").value(true));
    }

    @Test
    void getUnreadCount_shouldReturnCount() throws Exception {
        when(notificationService.getUnreadCount(100L)).thenReturn(5L);

        mockMvc.perform(get("/api/v1/notifications/user/100/unread-count"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").value(5));
    }
}
