package com.revworkforce.leaveservice.service.impl;

import com.revworkforce.leaveservice.dto.request.LeaveQuotaSetDto;
import com.revworkforce.leaveservice.dto.response.LeaveBalanceResponseDto;
import com.revworkforce.leaveservice.dto.response.LeaveTypeResponseDto;
import com.revworkforce.leaveservice.entity.LeaveBalance;
import com.revworkforce.leaveservice.entity.LeaveQuota;
import com.revworkforce.leaveservice.entity.LeaveType;
import com.revworkforce.leaveservice.exception.ResourceNotFoundException;
import com.revworkforce.leaveservice.repository.LeaveBalanceRepository;
import com.revworkforce.leaveservice.repository.LeaveQuotaRepository;
import com.revworkforce.leaveservice.repository.LeaveTypeRepository;
import com.revworkforce.leaveservice.service.LeaveBalanceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class LeaveBalanceServiceImpl implements LeaveBalanceService {

    private final LeaveBalanceRepository leaveBalanceRepository;
    private final LeaveQuotaRepository leaveQuotaRepository;
    private final LeaveTypeRepository leaveTypeRepository;

    public LeaveBalanceServiceImpl(LeaveBalanceRepository leaveBalanceRepository,
                                  LeaveQuotaRepository leaveQuotaRepository,
                                  LeaveTypeRepository leaveTypeRepository) {
        this.leaveBalanceRepository = leaveBalanceRepository;
        this.leaveQuotaRepository = leaveQuotaRepository;
        this.leaveTypeRepository = leaveTypeRepository;
    }

    @Override
    public void allocateYearlyQuota(LeaveQuotaSetDto requestDto) {
        LeaveType leaveType = leaveTypeRepository.findById(requestDto.getLeaveTypeId())
                .orElseThrow(() -> new ResourceNotFoundException("Leave type not found with id: " + requestDto.getLeaveTypeId()));

        LeaveQuota quota = leaveQuotaRepository.findByLeaveTypeIdAndYear(requestDto.getLeaveTypeId(), requestDto.getYear())
                .orElse(new LeaveQuota(leaveType, requestDto.getYear(), requestDto.getTotalDays()));

        quota.setTotalDays(requestDto.getTotalDays());
        leaveQuotaRepository.save(quota);
    }

    @Override
    public List<LeaveBalanceResponseDto> getEmployeeBalances(Long employeeId, int year) {
        initializeEmployeeBalancesForYear(employeeId, year);
        return leaveBalanceRepository.findByEmployeeIdAndYear(employeeId, year).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public LeaveBalanceResponseDto getEmployeeBalanceForType(Long employeeId, Long leaveTypeId, int year) {
        initializeEmployeeBalancesForYear(employeeId, year);
        LeaveBalance balance = leaveBalanceRepository.findByEmployeeIdAndLeaveTypeIdAndYear(employeeId, leaveTypeId, year)
                .orElseThrow(() -> new ResourceNotFoundException("Balance not found for employee " + employeeId + " and leave type " + leaveTypeId));
        return mapToDto(balance);
    }

    @Override
    public void initializeEmployeeBalancesForYear(Long employeeId, int year) {
        List<LeaveType> activeTypes = leaveTypeRepository.findByIsActiveTrue();
        for (LeaveType leaveType : activeTypes) {
            if (!leaveBalanceRepository.existsByEmployeeIdAndLeaveTypeIdAndYear(employeeId, leaveType.getId(), year)) {
                int totalDays = leaveQuotaRepository.findByLeaveTypeIdAndYear(leaveType.getId(), year)
                        .map(LeaveQuota::getTotalDays)
                        .orElse(12); // Default fallback 12 days if no specific quota defined

                LeaveBalance newBalance = new LeaveBalance(employeeId, leaveType, year, totalDays);
                leaveBalanceRepository.save(newBalance);
            }
        }
    }

    private LeaveBalanceResponseDto mapToDto(LeaveBalance entity) {
        LeaveTypeResponseDto typeDto = new LeaveTypeResponseDto(
                entity.getLeaveType().getId(),
                entity.getLeaveType().getName(),
                entity.getLeaveType().getCode(),
                entity.getLeaveType().getDescription(),
                entity.getLeaveType().isPaid(),
                entity.getLeaveType().isActive()
        );

        return new LeaveBalanceResponseDto(
                entity.getId(),
                entity.getEmployeeId(),
                typeDto,
                entity.getYear(),
                entity.getTotalDays(),
                entity.getUsedDays(),
                entity.getPendingDays(),
                entity.getRemainingDays()
        );
    }
}
