package com.revworkforce.leaveservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.revworkforce.leaveservice.dto.request.LeaveActionRequestDto;
import com.revworkforce.leaveservice.dto.request.LeaveApplyRequestDto;
import com.revworkforce.leaveservice.dto.response.LeaveResponseDto;
import com.revworkforce.leaveservice.entity.enums.LeaveStatus;
import com.revworkforce.leaveservice.service.LeaveRequestService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class LeaveRequestControllerTest {

    private MockMvc mockMvc;

    @Mock
    private LeaveRequestService leaveRequestService;

    @InjectMocks
    private LeaveRequestController leaveRequestController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(leaveRequestController).build();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    void applyLeave_shouldReturnCreated() throws Exception {
        LeaveApplyRequestDto requestDto = new LeaveApplyRequestDto(
                100L, 1L, LocalDate.now().plusDays(2), LocalDate.now().plusDays(4), "Vacation");

        LeaveResponseDto responseDto = new LeaveResponseDto();
        responseDto.setId(1L);
        responseDto.setEmployeeId(100L);
        responseDto.setTotalDays(3);
        responseDto.setStatus(LeaveStatus.PENDING);

        when(leaveRequestService.applyLeave(any(LeaveApplyRequestDto.class))).thenReturn(responseDto);

        mockMvc.perform(post("/api/v1/leave-requests/apply")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.id").value(1L))
                .andExpect(jsonPath("$.data.totalDays").value(3));
    }

    @Test
    void approveLeave_shouldReturnOk() throws Exception {
        LeaveActionRequestDto actionDto = new LeaveActionRequestDto(200L, "Approved");
        LeaveResponseDto responseDto = new LeaveResponseDto();
        responseDto.setId(1L);
        responseDto.setStatus(LeaveStatus.APPROVED);

        when(leaveRequestService.approveLeave(eq(1L), any(LeaveActionRequestDto.class))).thenReturn(responseDto);

        mockMvc.perform(patch("/api/v1/leave-requests/1/approve")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(actionDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.status").value("APPROVED"));
    }

    @Test
    void getEmployeeLeaveHistory_shouldReturnList() throws Exception {
        LeaveResponseDto responseDto = new LeaveResponseDto();
        responseDto.setId(1L);
        responseDto.setEmployeeId(100L);

        when(leaveRequestService.getEmployeeLeaveHistory(100L)).thenReturn(List.of(responseDto));

        mockMvc.perform(get("/api/v1/leave-requests/employee/100"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data[0].employeeId").value(100L));
    }
}
