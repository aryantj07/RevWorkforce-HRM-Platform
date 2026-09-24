package com.revworkforce.leaveservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public class HolidayCreateDto {

    @NotBlank(message = "Holiday name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;

    @NotNull(message = "Holiday date is required")
    private LocalDate holidayDate;

    @Size(max = 255, message = "Description cannot exceed 255 characters")
    private String description;

    private boolean isRecurring = false;

    public HolidayCreateDto() {
    }

    public HolidayCreateDto(String name, LocalDate holidayDate, String description, boolean isRecurring) {
        this.name = name;
        this.holidayDate = holidayDate;
        this.description = description;
        this.isRecurring = isRecurring;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getHolidayDate() {
        return holidayDate;
    }

    public void setHolidayDate(LocalDate holidayDate) {
        this.holidayDate = holidayDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isRecurring() {
        return isRecurring;
    }

    public void setRecurring(boolean recurring) {
        isRecurring = recurring;
    }
}
