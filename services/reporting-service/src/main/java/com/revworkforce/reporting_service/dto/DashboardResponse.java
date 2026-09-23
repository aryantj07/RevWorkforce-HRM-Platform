package com.revworkforce.reporting_service.dto;

public class DashboardResponse {

    private long totalEmployees;
    private long activeEmployees;
    private long inactiveEmployees;

    private LeaveSummaryResponse leaveSummary;
    private PerformanceSummaryResponse performanceSummary;

    public DashboardResponse() {
    }

    public long getTotalEmployees() {
        return totalEmployees;
    }

    public void setTotalEmployees(long totalEmployees) {
        this.totalEmployees = totalEmployees;
    }

    public long getActiveEmployees() {
        return activeEmployees;
    }

    public void setActiveEmployees(long activeEmployees) {
        this.activeEmployees = activeEmployees;
    }

    public long getInactiveEmployees() {
        return inactiveEmployees;
    }

    public void setInactiveEmployees(long inactiveEmployees) {
        this.inactiveEmployees = inactiveEmployees;
    }

    public LeaveSummaryResponse getLeaveSummary() {
        return leaveSummary;
    }

    public void setLeaveSummary(LeaveSummaryResponse leaveSummary) {
        this.leaveSummary = leaveSummary;
    }

    public PerformanceSummaryResponse getPerformanceSummary() {
        return performanceSummary;
    }

    public void setPerformanceSummary(
            PerformanceSummaryResponse performanceSummary) {
        this.performanceSummary = performanceSummary;
    }
}