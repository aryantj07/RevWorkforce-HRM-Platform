package com.revworkforce.leaveservice.service;

import com.revworkforce.leaveservice.dto.request.LeaveActionRequestDto;
import com.revworkforce.leaveservice.dto.request.LeaveApplyRequestDto;
import com.revworkforce.leaveservice.dto.response.LeaveResponseDto;
import com.revworkforce.leaveservice.entity.LeaveBalance;
import com.revworkforce.leaveservice.entity.LeaveRequest;
import com.revworkforce.leaveservice.entity.LeaveType;
import com.revworkforce.leaveservice.entity.enums.LeaveStatus;
import com.revworkforce.leaveservice.exception.InsufficientLeaveBalanceException;
import com.revworkforce.leaveservice.exception.InvalidLeaveRequestException;
import com.revworkforce.leaveservice.repository.LeaveBalanceRepository;
import com.revworkforce.leaveservice.repository.LeaveRequestRepository;
import com.revworkforce.leaveservice.repository.LeaveTypeRepository;
import com.revworkforce.leaveservice.service.impl.LeaveRequestServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LeaveRequestServiceTest {

    @Mock
    private LeaveRequestRepository leaveRequestRepository;

    @Mock
    private LeaveTypeRepository leaveTypeRepository;

    @Mock
    private LeaveBalanceRepository leaveBalanceRepository;

    @Mock
    private HolidayService holidayService;

    @Mock
    private LeaveBalanceService leaveBalanceService;

    @InjectMocks
    private LeaveRequestServiceImpl leaveRequestService;

    private LeaveType leaveType;
    private LeaveBalance leaveBalance;

    @BeforeEach
    void setUp() {
        leaveType = new LeaveType("Casual Leave", "CASUAL", "Casual days", true, true);
        leaveType.setId(1L);

        leaveBalance = new LeaveBalance(100L, leaveType, 2026, 12);
        leaveBalance.setId(10L);
    }

    @Test
    void applyLeave_successful() {
        LocalDate start = LocalDate.of(2026, 9, 1);
        LocalDate end = LocalDate.of(2026, 9, 3);
        LeaveApplyRequestDto requestDto = new LeaveApplyRequestDto(100L, 1L, start, end, "Attending family function");

        when(holidayService.calculateWorkingDays(start, end)).thenReturn(3);
        when(leaveRequestRepository.findOverlappingRequests(100L, start, end)).thenReturn(Collections.emptyList());
        when(leaveTypeRepository.findById(1L)).thenReturn(Optional.of(leaveType));
        when(leaveBalanceRepository.findByEmployeeIdAndLeaveTypeIdAndYear(100L, 1L, 2026)).thenReturn(Optional.of(leaveBalance));

        LeaveRequest savedRequest = new LeaveRequest(100L, leaveType, start, end, 3, "Attending family function");
        savedRequest.setId(1L);
        when(leaveRequestRepository.save(any(LeaveRequest.class))).thenReturn(savedRequest);

        LeaveResponseDto result = leaveRequestService.applyLeave(requestDto);

        assertNotNull(result);
        assertEquals(3, result.getTotalDays());
        assertEquals(LeaveStatus.PENDING, result.getStatus());
        assertEquals(3, leaveBalance.getPendingDays());
        verify(leaveBalanceRepository).save(leaveBalance);
    }

    @Test
    void applyLeave_shouldThrowWhenInsufficientBalance() {
        LocalDate start = LocalDate.of(2026, 9, 1);
        LocalDate end = LocalDate.of(2026, 9, 30);
        LeaveApplyRequestDto requestDto = new LeaveApplyRequestDto(100L, 1L, start, end, "Long vacation");

        when(holidayService.calculateWorkingDays(start, end)).thenReturn(22);
        when(leaveRequestRepository.findOverlappingRequests(100L, start, end)).thenReturn(Collections.emptyList());
        when(leaveTypeRepository.findById(1L)).thenReturn(Optional.of(leaveType));
        when(leaveBalanceRepository.findByEmployeeIdAndLeaveTypeIdAndYear(100L, 1L, 2026)).thenReturn(Optional.of(leaveBalance));

        assertThrows(InsufficientLeaveBalanceException.class, () -> leaveRequestService.applyLeave(requestDto));
    }

    @Test
    void approveLeave_successful() {
        LeaveRequest request = new LeaveRequest(100L, leaveType, LocalDate.of(2026, 9, 1), LocalDate.of(2026, 9, 3), 3, "Trip");
        request.setId(1L);
        request.setStatus(LeaveStatus.PENDING);
        leaveBalance.setPendingDays(3);

        when(leaveRequestRepository.findById(1L)).thenReturn(Optional.of(request));
        when(leaveBalanceRepository.findByEmployeeIdAndLeaveTypeIdAndYear(100L, 1L, 2026)).thenReturn(Optional.of(leaveBalance));
        when(leaveRequestRepository.save(any(LeaveRequest.class))).thenAnswer(i -> i.getArgument(0));

        LeaveActionRequestDto actionDto = new LeaveActionRequestDto(200L, "Approved, enjoy!");
        LeaveResponseDto result = leaveRequestService.approveLeave(1L, actionDto);

        assertEquals(LeaveStatus.APPROVED, result.getStatus());
        assertEquals(200L, result.getApproverId());
        assertEquals(0, leaveBalance.getPendingDays());
        assertEquals(3, leaveBalance.getUsedDays());
    }

    @Test
    void rejectLeave_successful() {
        LeaveRequest request = new LeaveRequest(100L, leaveType, LocalDate.of(2026, 9, 1), LocalDate.of(2026, 9, 3), 3, "Trip");
        request.setId(1L);
        request.setStatus(LeaveStatus.PENDING);
        leaveBalance.setPendingDays(3);

        when(leaveRequestRepository.findById(1L)).thenReturn(Optional.of(request));
        when(leaveBalanceRepository.findByEmployeeIdAndLeaveTypeIdAndYear(100L, 1L, 2026)).thenReturn(Optional.of(leaveBalance));
        when(leaveRequestRepository.save(any(LeaveRequest.class))).thenAnswer(i -> i.getArgument(0));

        LeaveActionRequestDto actionDto = new LeaveActionRequestDto(200L, "Critical sprint deadline");
        LeaveResponseDto result = leaveRequestService.rejectLeave(1L, actionDto);

        assertEquals(LeaveStatus.REJECTED, result.getStatus());
        assertEquals(0, leaveBalance.getPendingDays());
        assertEquals(0, leaveBalance.getUsedDays());
    }

    @Test
    void cancelLeave_successful() {
        LeaveRequest request = new LeaveRequest(100L, leaveType, LocalDate.of(2026, 9, 1), LocalDate.of(2026, 9, 3), 3, "Trip");
        request.setId(1L);
        request.setStatus(LeaveStatus.PENDING);
        leaveBalance.setPendingDays(3);

        when(leaveRequestRepository.findById(1L)).thenReturn(Optional.of(request));
        when(leaveBalanceRepository.findByEmployeeIdAndLeaveTypeIdAndYear(100L, 1L, 2026)).thenReturn(Optional.of(leaveBalance));
        when(leaveRequestRepository.save(any(LeaveRequest.class))).thenAnswer(i -> i.getArgument(0));

        LeaveResponseDto result = leaveRequestService.cancelLeave(1L, 100L);

        assertEquals(LeaveStatus.CANCELLED, result.getStatus());
        assertEquals(0, leaveBalance.getPendingDays());
    }
}
