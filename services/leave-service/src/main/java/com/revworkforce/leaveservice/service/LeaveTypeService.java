package com.revworkforce.leaveservice.service;

import com.revworkforce.leaveservice.dto.request.LeaveTypeCreateDto;
import com.revworkforce.leaveservice.dto.response.LeaveTypeResponseDto;

import java.util.List;

public interface LeaveTypeService {
    LeaveTypeResponseDto createLeaveType(LeaveTypeCreateDto requestDto);
    List<LeaveTypeResponseDto> getAllLeaveTypes();
    List<LeaveTypeResponseDto> getActiveLeaveTypes();
    LeaveTypeResponseDto getLeaveTypeById(Long id);
    LeaveTypeResponseDto updateLeaveTypeStatus(Long id, boolean isActive);
}
