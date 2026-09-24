package com.revworkforce.leaveservice.service.impl;

import com.revworkforce.leaveservice.dto.request.LeaveActionRequestDto;
import com.revworkforce.leaveservice.dto.request.LeaveApplyRequestDto;
import com.revworkforce.leaveservice.dto.response.LeaveResponseDto;
import com.revworkforce.leaveservice.dto.response.LeaveTypeResponseDto;
import com.revworkforce.leaveservice.entity.LeaveBalance;
import com.revworkforce.leaveservice.entity.LeaveRequest;
import com.revworkforce.leaveservice.entity.LeaveType;
import com.revworkforce.leaveservice.entity.enums.LeaveStatus;
import com.revworkforce.leaveservice.exception.InsufficientLeaveBalanceException;
import com.revworkforce.leaveservice.exception.InvalidLeaveRequestException;
import com.revworkforce.leaveservice.exception.ResourceNotFoundException;
import com.revworkforce.leaveservice.repository.LeaveBalanceRepository;
import com.revworkforce.leaveservice.repository.LeaveRequestRepository;
import com.revworkforce.leaveservice.repository.LeaveTypeRepository;
import com.revworkforce.leaveservice.service.HolidayService;
import com.revworkforce.leaveservice.service.LeaveBalanceService;
import com.revworkforce.leaveservice.service.LeaveRequestService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class LeaveRequestServiceImpl implements LeaveRequestService {

    private final LeaveRequestRepository leaveRequestRepository;
    private final LeaveTypeRepository leaveTypeRepository;
    private final LeaveBalanceRepository leaveBalanceRepository;
    private final HolidayService holidayService;
    private final LeaveBalanceService leaveBalanceService;

    public LeaveRequestServiceImpl(LeaveRequestRepository leaveRequestRepository,
                                  LeaveTypeRepository leaveTypeRepository,
                                  LeaveBalanceRepository leaveBalanceRepository,
                                  HolidayService holidayService,
                                  LeaveBalanceService leaveBalanceService) {
        this.leaveRequestRepository = leaveRequestRepository;
        this.leaveTypeRepository = leaveTypeRepository;
        this.leaveBalanceRepository = leaveBalanceRepository;
        this.holidayService = holidayService;
        this.leaveBalanceService = leaveBalanceService;
    }

    @Override
    public LeaveResponseDto applyLeave(LeaveApplyRequestDto requestDto) {
        if (requestDto.getStartDate().isAfter(requestDto.getEndDate())) {
            throw new InvalidLeaveRequestException("Start date cannot be after end date");
        }

        // 1. Calculate working days (excluding weekends & holidays)
        int requestedDays = holidayService.calculateWorkingDays(requestDto.getStartDate(), requestDto.getEndDate());
        if (requestedDays <= 0) {
            throw new InvalidLeaveRequestException("Selected date range contains 0 working days (all weekends or public holidays)");
        }

        // 2. Validate overlapping leaves
        List<LeaveRequest> overlapping = leaveRequestRepository.findOverlappingRequests(
                requestDto.getEmployeeId(), requestDto.getStartDate(), requestDto.getEndDate());
        if (!overlapping.isEmpty()) {
            throw new InvalidLeaveRequestException("You already have an active leave request overlapping with the selected dates");
        }

        // 3. Validate leave type
        LeaveType leaveType = leaveTypeRepository.findById(requestDto.getLeaveTypeId())
                .orElseThrow(() -> new ResourceNotFoundException("Leave type not found with id: " + requestDto.getLeaveTypeId()));

        if (!leaveType.isActive()) {
            throw new InvalidLeaveRequestException("The selected leave type is currently inactive");
        }

        // 4. Validate and update employee balance
        int year = requestDto.getStartDate().getYear();
        leaveBalanceService.initializeEmployeeBalancesForYear(requestDto.getEmployeeId(), year);

        LeaveBalance balance = leaveBalanceRepository.findByEmployeeIdAndLeaveTypeIdAndYear(
                requestDto.getEmployeeId(), leaveType.getId(), year)
                .orElseThrow(() -> new ResourceNotFoundException("No leave balance found for employee " + requestDto.getEmployeeId()));

        if (balance.getRemainingDays() < requestedDays) {
            throw new InsufficientLeaveBalanceException(String.format(
                    "Insufficient leave balance. Requested: %d days, Remaining: %d days",
                    requestedDays, balance.getRemainingDays()));
        }

        // Increase pending days
        balance.setPendingDays(balance.getPendingDays() + requestedDays);
        leaveBalanceRepository.save(balance);

        // 5. Save leave request
        LeaveRequest leaveRequest = new LeaveRequest(
                requestDto.getEmployeeId(),
                leaveType,
                requestDto.getStartDate(),
                requestDto.getEndDate(),
                requestedDays,
                requestDto.getReason().trim()
        );

        LeaveRequest saved = leaveRequestRepository.save(leaveRequest);
        return mapToDto(saved);
    }

    @Override
    public LeaveResponseDto approveLeave(Long leaveId, LeaveActionRequestDto actionDto) {
        LeaveRequest leaveRequest = leaveRequestRepository.findById(leaveId)
                .orElseThrow(() -> new ResourceNotFoundException("Leave request not found with id: " + leaveId));

        if (leaveRequest.getStatus() != LeaveStatus.PENDING) {
            throw new InvalidLeaveRequestException("Only PENDING leave requests can be approved. Current status: " + leaveRequest.getStatus());
        }

        int year = leaveRequest.getStartDate().getYear();
        LeaveBalance balance = leaveBalanceRepository.findByEmployeeIdAndLeaveTypeIdAndYear(
                leaveRequest.getEmployeeId(), leaveRequest.getLeaveType().getId(), year)
                .orElseThrow(() -> new ResourceNotFoundException("Leave balance record not found"));

        // Deduct from pending, add to used
        balance.setPendingDays(Math.max(0, balance.getPendingDays() - leaveRequest.getTotalDays()));
        balance.setUsedDays(balance.getUsedDays() + leaveRequest.getTotalDays());
        leaveBalanceRepository.save(balance);

        leaveRequest.setStatus(LeaveStatus.APPROVED);
        leaveRequest.setApproverId(actionDto.getApproverId());
        leaveRequest.setApproverComments(actionDto.getComments());
        leaveRequest.setReviewedAt(LocalDateTime.now());

        LeaveRequest updated = leaveRequestRepository.save(leaveRequest);
        return mapToDto(updated);
    }

    @Override
    public LeaveResponseDto rejectLeave(Long leaveId, LeaveActionRequestDto actionDto) {
        LeaveRequest leaveRequest = leaveRequestRepository.findById(leaveId)
                .orElseThrow(() -> new ResourceNotFoundException("Leave request not found with id: " + leaveId));

        if (leaveRequest.getStatus() != LeaveStatus.PENDING) {
            throw new InvalidLeaveRequestException("Only PENDING leave requests can be rejected. Current status: " + leaveRequest.getStatus());
        }

        int year = leaveRequest.getStartDate().getYear();
        LeaveBalance balance = leaveBalanceRepository.findByEmployeeIdAndLeaveTypeIdAndYear(
                leaveRequest.getEmployeeId(), leaveRequest.getLeaveType().getId(), year)
                .orElseThrow(() -> new ResourceNotFoundException("Leave balance record not found"));

        // Restore pending days
        balance.setPendingDays(Math.max(0, balance.getPendingDays() - leaveRequest.getTotalDays()));
        leaveBalanceRepository.save(balance);

        leaveRequest.setStatus(LeaveStatus.REJECTED);
        leaveRequest.setApproverId(actionDto.getApproverId());
        leaveRequest.setApproverComments(actionDto.getComments());
        leaveRequest.setReviewedAt(LocalDateTime.now());

        LeaveRequest updated = leaveRequestRepository.save(leaveRequest);
        return mapToDto(updated);
    }

    @Override
    public LeaveResponseDto cancelLeave(Long leaveId, Long employeeId) {
        LeaveRequest leaveRequest = leaveRequestRepository.findById(leaveId)
                .orElseThrow(() -> new ResourceNotFoundException("Leave request not found with id: " + leaveId));

        if (!leaveRequest.getEmployeeId().equals(employeeId)) {
            throw new InvalidLeaveRequestException("You can only cancel your own leave requests");
        }

        if (leaveRequest.getStatus() == LeaveStatus.CANCELLED || leaveRequest.getStatus() == LeaveStatus.REJECTED) {
            throw new InvalidLeaveRequestException("Cannot cancel a leave request with status: " + leaveRequest.getStatus());
        }

        int year = leaveRequest.getStartDate().getYear();
        LeaveBalance balance = leaveBalanceRepository.findByEmployeeIdAndLeaveTypeIdAndYear(
                leaveRequest.getEmployeeId(), leaveRequest.getLeaveType().getId(), year)
                .orElseThrow(() -> new ResourceNotFoundException("Leave balance record not found"));

        if (leaveRequest.getStatus() == LeaveStatus.PENDING) {
            balance.setPendingDays(Math.max(0, balance.getPendingDays() - leaveRequest.getTotalDays()));
        } else if (leaveRequest.getStatus() == LeaveStatus.APPROVED) {
            balance.setUsedDays(Math.max(0, balance.getUsedDays() - leaveRequest.getTotalDays()));
        }

        leaveBalanceRepository.save(balance);

        leaveRequest.setStatus(LeaveStatus.CANCELLED);
        leaveRequest.setReviewedAt(LocalDateTime.now());

        LeaveRequest updated = leaveRequestRepository.save(leaveRequest);
        return mapToDto(updated);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LeaveResponseDto> getEmployeeLeaveHistory(Long employeeId) {
        return leaveRequestRepository.findByEmployeeIdOrderByAppliedAtDesc(employeeId).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<LeaveResponseDto> getLeavesByStatus(LeaveStatus status) {
        return leaveRequestRepository.findByStatusOrderByAppliedAtAsc(status).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public LeaveResponseDto getLeaveById(Long leaveId) {
        LeaveRequest leaveRequest = leaveRequestRepository.findById(leaveId)
                .orElseThrow(() -> new ResourceNotFoundException("Leave request not found with id: " + leaveId));
        return mapToDto(leaveRequest);
    }

    private LeaveResponseDto mapToDto(LeaveRequest entity) {
        LeaveResponseDto dto = new LeaveResponseDto();
        dto.setId(entity.getId());
        dto.setEmployeeId(entity.getEmployeeId());
        dto.setStartDate(entity.getStartDate());
        dto.setEndDate(entity.getEndDate());
        dto.setTotalDays(entity.getTotalDays());
        dto.setReason(entity.getReason());
        dto.setStatus(entity.getStatus());
        dto.setApproverId(entity.getApproverId());
        dto.setApproverComments(entity.getApproverComments());
        dto.setAppliedAt(entity.getAppliedAt());
        dto.setReviewedAt(entity.getReviewedAt());

        if (entity.getLeaveType() != null) {
            LeaveTypeResponseDto typeDto = new LeaveTypeResponseDto(
                    entity.getLeaveType().getId(),
                    entity.getLeaveType().getName(),
                    entity.getLeaveType().getCode(),
                    entity.getLeaveType().getDescription(),
                    entity.getLeaveType().isPaid(),
                    entity.getLeaveType().isActive()
            );
            dto.setLeaveType(typeDto);
        }

        return dto;
    }
}
