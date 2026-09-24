package com.revworkforce.leaveservice.service;

import com.revworkforce.leaveservice.dto.request.HolidayCreateDto;
import com.revworkforce.leaveservice.dto.response.HolidayResponseDto;

import java.time.LocalDate;
import java.util.List;

public interface HolidayService {
    HolidayResponseDto createHoliday(HolidayCreateDto requestDto);
    List<HolidayResponseDto> getAllHolidays();
    List<HolidayResponseDto> getHolidaysBetween(LocalDate startDate, LocalDate endDate);
    void deleteHoliday(Long id);
    int calculateWorkingDays(LocalDate startDate, LocalDate endDate);
}
