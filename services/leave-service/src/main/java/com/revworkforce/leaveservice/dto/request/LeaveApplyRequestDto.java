package com.revworkforce.leaveservice.dto.request;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public class LeaveApplyRequestDto {

    @NotNull(message = "Employee ID is required")
    private Long employeeId;

    @NotNull(message = "Leave Type ID is required")
    private Long leaveTypeId;

    @NotNull(message = "Start Date is required")
    @FutureOrPresent(message = "Start Date must be today or in the future")
    private LocalDate startDate;

    @NotNull(message = "End Date is required")
    @FutureOrPresent(message = "End Date must be today or in the future")
    private LocalDate endDate;

    @NotBlank(message = "Reason is required")
    @Size(min = 3, max = 500, message = "Reason must be between 3 and 500 characters")
    private String reason;

    public LeaveApplyRequestDto() {
    }

    public LeaveApplyRequestDto(Long employeeId, Long leaveTypeId, LocalDate startDate, LocalDate endDate, String reason) {
        this.employeeId = employeeId;
        this.leaveTypeId = leaveTypeId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.reason = reason;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public Long getLeaveTypeId() {
        return leaveTypeId;
    }

    public void setLeaveTypeId(Long leaveTypeId) {
        this.leaveTypeId = leaveTypeId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
