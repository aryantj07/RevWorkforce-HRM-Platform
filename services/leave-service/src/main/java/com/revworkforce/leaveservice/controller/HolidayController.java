package com.revworkforce.leaveservice.controller;

import com.revworkforce.leaveservice.dto.request.HolidayCreateDto;
import com.revworkforce.leaveservice.dto.response.ApiResponse;
import com.revworkforce.leaveservice.dto.response.HolidayResponseDto;
import com.revworkforce.leaveservice.service.HolidayService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/holidays")
@Tag(name = "Holidays", description = "Endpoints for managing company and public holidays")
public class HolidayController {

    private final HolidayService holidayService;

    public HolidayController(HolidayService holidayService) {
        this.holidayService = holidayService;
    }

    @PostMapping
    @Operation(summary = "Create a company holiday")
    public ResponseEntity<ApiResponse<HolidayResponseDto>> createHoliday(@Valid @RequestBody HolidayCreateDto requestDto) {
        HolidayResponseDto created = holidayService.createHoliday(requestDto);
        return new ResponseEntity<>(ApiResponse.success("Holiday created successfully", created), HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Get all holidays")
    public ResponseEntity<ApiResponse<List<HolidayResponseDto>>> getAllHolidays() {
        return ResponseEntity.ok(ApiResponse.success(holidayService.getAllHolidays()));
    }

    @GetMapping("/range")
    @Operation(summary = "Get holidays between specific dates")
    public ResponseEntity<ApiResponse<List<HolidayResponseDto>>> getHolidaysBetween(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(ApiResponse.success(holidayService.getHolidaysBetween(startDate, endDate)));
    }

    @GetMapping("/working-days")
    @Operation(summary = "Calculate net working days between dates excluding weekends and holidays")
    public ResponseEntity<ApiResponse<Integer>> calculateWorkingDays(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        int days = holidayService.calculateWorkingDays(startDate, endDate);
        return ResponseEntity.ok(ApiResponse.success("Working days calculated", days));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a holiday by ID")
    public ResponseEntity<ApiResponse<Void>> deleteHoliday(@PathVariable Long id) {
        holidayService.deleteHoliday(id);
        return ResponseEntity.ok(ApiResponse.success("Holiday deleted successfully", null));
    }
}
