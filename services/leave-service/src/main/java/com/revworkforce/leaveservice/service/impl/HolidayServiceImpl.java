package com.revworkforce.leaveservice.service.impl;

import com.revworkforce.leaveservice.dto.request.HolidayCreateDto;
import com.revworkforce.leaveservice.dto.response.HolidayResponseDto;
import com.revworkforce.leaveservice.entity.Holiday;
import com.revworkforce.leaveservice.exception.InvalidLeaveRequestException;
import com.revworkforce.leaveservice.exception.ResourceNotFoundException;
import com.revworkforce.leaveservice.repository.HolidayRepository;
import com.revworkforce.leaveservice.service.HolidayService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class HolidayServiceImpl implements HolidayService {

    private final HolidayRepository holidayRepository;

    public HolidayServiceImpl(HolidayRepository holidayRepository) {
        this.holidayRepository = holidayRepository;
    }

    @Override
    public HolidayResponseDto createHoliday(HolidayCreateDto requestDto) {
        if (holidayRepository.existsByHolidayDate(requestDto.getHolidayDate())) {
            throw new InvalidLeaveRequestException("A holiday is already configured for date: " + requestDto.getHolidayDate());
        }

        Holiday holiday = new Holiday(
                requestDto.getName().trim(),
                requestDto.getHolidayDate(),
                requestDto.getDescription(),
                requestDto.isRecurring()
        );

        Holiday saved = holidayRepository.save(holiday);
        return mapToDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<HolidayResponseDto> getAllHolidays() {
        return holidayRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<HolidayResponseDto> getHolidaysBetween(LocalDate startDate, LocalDate endDate) {
        return holidayRepository.findByHolidayDateBetween(startDate, endDate).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteHoliday(Long id) {
        if (!holidayRepository.existsById(id)) {
            throw new ResourceNotFoundException("Holiday not found with id: " + id);
        }
        holidayRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public int calculateWorkingDays(LocalDate startDate, LocalDate endDate) {
        if (startDate.isAfter(endDate)) {
            throw new InvalidLeaveRequestException("Start date cannot be after end date");
        }

        List<Holiday> holidays = holidayRepository.findByHolidayDateBetween(startDate, endDate);
        Set<LocalDate> holidayDates = holidays.stream()
                .map(Holiday::getHolidayDate)
                .collect(Collectors.toSet());

        int workingDays = 0;
        LocalDate current = startDate;
        while (!current.isAfter(endDate)) {
            DayOfWeek day = current.getDayOfWeek();
            boolean isWeekend = (day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY);
            boolean isHoliday = holidayDates.contains(current);

            if (!isWeekend && !isHoliday) {
                workingDays++;
            }
            current = current.plusDays(1);
        }

        return workingDays;
    }

    private HolidayResponseDto mapToDto(Holiday entity) {
        return new HolidayResponseDto(
                entity.getId(),
                entity.getName(),
                entity.getHolidayDate(),
                entity.getDescription(),
                entity.isRecurring()
        );
    }
}
