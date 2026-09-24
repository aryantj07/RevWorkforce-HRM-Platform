package com.revworkforce.leaveservice.dto.response;

public class LeaveBalanceResponseDto {

    private Long id;
    private Long employeeId;
    private LeaveTypeResponseDto leaveType;
    private int year;
    private int totalDays;
    private int usedDays;
    private int pendingDays;
    private int remainingDays;

    public LeaveBalanceResponseDto() {
    }

    public LeaveBalanceResponseDto(Long id, Long employeeId, LeaveTypeResponseDto leaveType, int year, int totalDays, int usedDays, int pendingDays, int remainingDays) {
        this.id = id;
        this.employeeId = employeeId;
        this.leaveType = leaveType;
        this.year = year;
        this.totalDays = totalDays;
        this.usedDays = usedDays;
        this.pendingDays = pendingDays;
        this.remainingDays = remainingDays;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public LeaveTypeResponseDto getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(LeaveTypeResponseDto leaveType) {
        this.leaveType = leaveType;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getTotalDays() {
        return totalDays;
    }

    public void setTotalDays(int totalDays) {
        this.totalDays = totalDays;
    }

    public int getUsedDays() {
        return usedDays;
    }

    public void setUsedDays(int usedDays) {
        this.usedDays = usedDays;
    }

    public int getPendingDays() {
        return pendingDays;
    }

    public void setPendingDays(int pendingDays) {
        this.pendingDays = pendingDays;
    }

    public int getRemainingDays() {
        return remainingDays;
    }

    public void setRemainingDays(int remainingDays) {
        this.remainingDays = remainingDays;
    }
}
