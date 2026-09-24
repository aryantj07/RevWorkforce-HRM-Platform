package com.revworkforce.leaveservice.service.impl;

import com.revworkforce.leaveservice.dto.request.LeaveTypeCreateDto;
import com.revworkforce.leaveservice.dto.response.LeaveTypeResponseDto;
import com.revworkforce.leaveservice.entity.LeaveType;
import com.revworkforce.leaveservice.exception.InvalidLeaveRequestException;
import com.revworkforce.leaveservice.exception.ResourceNotFoundException;
import com.revworkforce.leaveservice.repository.LeaveTypeRepository;
import com.revworkforce.leaveservice.service.LeaveTypeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class LeaveTypeServiceImpl implements LeaveTypeService {

    private final LeaveTypeRepository leaveTypeRepository;

    public LeaveTypeServiceImpl(LeaveTypeRepository leaveTypeRepository) {
        this.leaveTypeRepository = leaveTypeRepository;
    }

    @Override
    public LeaveTypeResponseDto createLeaveType(LeaveTypeCreateDto requestDto) {
        if (leaveTypeRepository.existsByCode(requestDto.getCode().toUpperCase().trim())) {
            throw new InvalidLeaveRequestException("Leave type with code " + requestDto.getCode() + " already exists");
        }
        if (leaveTypeRepository.existsByNameIgnoreCase(requestDto.getName().trim())) {
            throw new InvalidLeaveRequestException("Leave type with name " + requestDto.getName() + " already exists");
        }

        LeaveType leaveType = new LeaveType(
                requestDto.getName().trim(),
                requestDto.getCode().toUpperCase().trim(),
                requestDto.getDescription(),
                requestDto.isPaid(),
                requestDto.isActive()
        );

        LeaveType saved = leaveTypeRepository.save(leaveType);
        return mapToDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LeaveTypeResponseDto> getAllLeaveTypes() {
        return leaveTypeRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<LeaveTypeResponseDto> getActiveLeaveTypes() {
        return leaveTypeRepository.findByIsActiveTrue().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public LeaveTypeResponseDto getLeaveTypeById(Long id) {
        LeaveType leaveType = leaveTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Leave type not found with id: " + id));
        return mapToDto(leaveType);
    }

    @Override
    public LeaveTypeResponseDto updateLeaveTypeStatus(Long id, boolean isActive) {
        LeaveType leaveType = leaveTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Leave type not found with id: " + id));
        leaveType.setActive(isActive);
        LeaveType updated = leaveTypeRepository.save(leaveType);
        return mapToDto(updated);
    }

    private LeaveTypeResponseDto mapToDto(LeaveType entity) {
        return new LeaveTypeResponseDto(
                entity.getId(),
                entity.getName(),
                entity.getCode(),
                entity.getDescription(),
                entity.isPaid(),
                entity.isActive()
        );
    }
}
