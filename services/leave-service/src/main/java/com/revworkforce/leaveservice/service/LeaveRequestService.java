package com.revworkforce.leaveservice.service;

import com.revworkforce.leaveservice.dto.request.LeaveActionRequestDto;
import com.revworkforce.leaveservice.dto.request.LeaveApplyRequestDto;
import com.revworkforce.leaveservice.dto.response.LeaveResponseDto;
import com.revworkforce.leaveservice.entity.enums.LeaveStatus;

import java.util.List;

public interface LeaveRequestService {
    LeaveResponseDto applyLeave(LeaveApplyRequestDto requestDto);
    LeaveResponseDto approveLeave(Long leaveId, LeaveActionRequestDto actionDto);
    LeaveResponseDto rejectLeave(Long leaveId, LeaveActionRequestDto actionDto);
    LeaveResponseDto cancelLeave(Long leaveId, Long employeeId);
    List<LeaveResponseDto> getEmployeeLeaveHistory(Long employeeId);
    List<LeaveResponseDto> getLeavesByStatus(LeaveStatus status);
    LeaveResponseDto getLeaveById(Long leaveId);
}
