package com.revworkforce.reporting_service.dto;

public class PerformanceSummaryResponse {

    private long totalReviews;
    private long completedReviews;
    private long pendingReviews;
    private double averageRating;

    public PerformanceSummaryResponse() {
    }

    public long getTotalReviews() {
        return totalReviews;
    }

    public void setTotalReviews(long totalReviews) {
        this.totalReviews = totalReviews;
    }

    public long getCompletedReviews() {
        return completedReviews;
    }

    public void setCompletedReviews(long completedReviews) {
        this.completedReviews = completedReviews;
    }

    public long getPendingReviews() {
        return pendingReviews;
    }

    public void setPendingReviews(long pendingReviews) {
        this.pendingReviews = pendingReviews;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }
}