package com.revworkforce.leaveservice.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class LeaveQuotaSetDto {

    @NotNull(message = "Leave Type ID is required")
    private Long leaveTypeId;

    @NotNull(message = "Year is required")
    @Min(value = 2000, message = "Year must be valid")
    private Integer year;

    @NotNull(message = "Total days is required")
    @Min(value = 1, message = "Total days must be at least 1")
    private Integer totalDays;

    public LeaveQuotaSetDto() {
    }

    public LeaveQuotaSetDto(Long leaveTypeId, Integer year, Integer totalDays) {
        this.leaveTypeId = leaveTypeId;
        this.year = year;
        this.totalDays = totalDays;
    }

    public Long getLeaveTypeId() {
        return leaveTypeId;
    }

    public void setLeaveTypeId(Long leaveTypeId) {
        this.leaveTypeId = leaveTypeId;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getTotalDays() {
        return totalDays;
    }

    public void setTotalDays(Integer totalDays) {
        this.totalDays = totalDays;
    }
}
