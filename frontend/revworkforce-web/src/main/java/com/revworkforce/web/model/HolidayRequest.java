package com.revworkforce.web.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public class HolidayRequest {

    private String name;

    private LocalDate holidayDate;

    private String description;

    @JsonProperty("isRecurring")
    private boolean recurring;

    public HolidayRequest() {
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
        return recurring;
    }

    public void setRecurring(boolean recurring) {
        this.recurring = recurring;
    }
}