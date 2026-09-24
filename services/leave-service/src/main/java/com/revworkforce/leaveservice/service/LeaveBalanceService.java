package com.revworkforce.leaveservice.service;

import com.revworkforce.leaveservice.dto.request.LeaveQuotaSetDto;
import com.revworkforce.leaveservice.dto.response.LeaveBalanceResponseDto;

import java.util.List;

public interface LeaveBalanceService {
    void allocateYearlyQuota(LeaveQuotaSetDto requestDto);
    List<LeaveBalanceResponseDto> getEmployeeBalances(Long employeeId, int year);
    LeaveBalanceResponseDto getEmployeeBalanceForType(Long employeeId, Long leaveTypeId, int year);
    void initializeEmployeeBalancesForYear(Long employeeId, int year);
}
