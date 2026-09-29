package com.revworkforce.web.model;

public class DashboardResponse {

    private long totalEmployees;
    private long activeEmployees;
    private long inactiveEmployees;

    private LeaveSummary leaveSummary;
    private PerformanceSummary performanceSummary;

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

    public LeaveSummary getLeaveSummary() {
        return leaveSummary;
    }

    public void setLeaveSummary(LeaveSummary leaveSummary) {
        this.leaveSummary = leaveSummary;
    }

    public PerformanceSummary getPerformanceSummary() {
        return performanceSummary;
    }

    public void setPerformanceSummary(PerformanceSummary performanceSummary) {
        this.performanceSummary = performanceSummary;
    }
}