package com.revworkforce.leaveservice.dto.response;

import java.time.LocalDate;

public class HolidayResponseDto {

    private Long id;
    private String name;
    private LocalDate holidayDate;
    private String description;
    private boolean isRecurring;

    public HolidayResponseDto() {
    }

    public HolidayResponseDto(Long id, String name, LocalDate holidayDate, String description, boolean isRecurring) {
        this.id = id;
        this.name = name;
        this.holidayDate = holidayDate;
        this.description = description;
        this.isRecurring = isRecurring;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
