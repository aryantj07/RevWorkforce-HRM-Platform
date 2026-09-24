package com.revworkforce.leaveservice.service;

import com.revworkforce.leaveservice.entity.Holiday;
import com.revworkforce.leaveservice.repository.HolidayRepository;
import com.revworkforce.leaveservice.service.impl.HolidayServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HolidayServiceTest {

    @Mock
    private HolidayRepository holidayRepository;

    @InjectMocks
    private HolidayServiceImpl holidayService;

    private Holiday sampleHoliday;

    @BeforeEach
    void setUp() {
        // Wednesday holiday
        sampleHoliday = new Holiday("Independence Day", LocalDate.of(2026, 8, 15), "National holiday", false);
    }

    @Test
    void calculateWorkingDays_shouldExcludeWeekends() {
        // Monday (Aug 3) to Friday (Aug 7) = 5 working days
        LocalDate start = LocalDate.of(2026, 8, 3);
        LocalDate end = LocalDate.of(2026, 8, 7);

        when(holidayRepository.findByHolidayDateBetween(start, end)).thenReturn(Collections.emptyList());

        int workingDays = holidayService.calculateWorkingDays(start, end);
        assertEquals(5, workingDays);
    }

    @Test
    void calculateWorkingDays_shouldExcludeWeekendsAndHolidays() {
        // Monday (Aug 10) to Sunday (Aug 16) -> 5 weekdays minus 1 holiday on Aug 15 (which is Saturday) = 5 working days
        // Let's test a holiday on Wednesday Aug 12
        LocalDate start = LocalDate.of(2026, 8, 10); // Monday
        LocalDate end = LocalDate.of(2026, 8, 14);   // Friday
        Holiday midWeekHoliday = new Holiday("Midweek Holiday", LocalDate.of(2026, 8, 12), "Desc", false);

        when(holidayRepository.findByHolidayDateBetween(start, end)).thenReturn(List.of(midWeekHoliday));

        int workingDays = holidayService.calculateWorkingDays(start, end);
        // 5 weekdays - 1 holiday = 4
        assertEquals(4, workingDays);
    }
}
