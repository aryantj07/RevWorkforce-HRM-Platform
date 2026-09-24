package com.revworkforce.leaveservice.dto.response;

public class LeaveSummaryResponseDto {

    private long totalApplications;
    private long approvedApplications;
    private long rejectedApplications;
    private long pendingApplications;
    private double utilizationPercentage;

    public LeaveSummaryResponseDto() {
    }

    public LeaveSummaryResponseDto(
            long totalApplications,
            long approvedApplications,
            long rejectedApplications,
            long pendingApplications,
            double utilizationPercentage) {

        this.totalApplications = totalApplications;
        this.approvedApplications = approvedApplications;
        this.rejectedApplications = rejectedApplications;
        this.pendingApplications = pendingApplications;
        this.utilizationPercentage = utilizationPercentage;
    }

    public long getTotalApplications() {
        return totalApplications;
    }

    public void setTotalApplications(long totalApplications) {
        this.totalApplications = totalApplications;
    }

    public long getApprovedApplications() {
        return approvedApplications;
    }

    public void setApprovedApplications(long approvedApplications) {
        this.approvedApplications = approvedApplications;
    }

    public long getRejectedApplications() {
        return rejectedApplications;
    }

    public void setRejectedApplications(long rejectedApplications) {
        this.rejectedApplications = rejectedApplications;
    }

    public long getPendingApplications() {
        return pendingApplications;
    }

    public void setPendingApplications(long pendingApplications) {
        this.pendingApplications = pendingApplications;
    }

    public double getUtilizationPercentage() {
        return utilizationPercentage;
    }

    public void setUtilizationPercentage(double utilizationPercentage) {
        this.utilizationPercentage = utilizationPercentage;
    }
}