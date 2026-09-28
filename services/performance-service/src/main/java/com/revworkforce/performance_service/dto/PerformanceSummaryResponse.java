package com.revworkforce.performance_service.dto;

public class PerformanceSummaryResponse {

    private long totalReviews;
    private long completedReviews;
    private long pendingReviews;
    private double averageRating;

    public PerformanceSummaryResponse() {
    }

    public PerformanceSummaryResponse(
            long totalReviews,
            long completedReviews,
            long pendingReviews,
            double averageRating) {

        this.totalReviews = totalReviews;
        this.completedReviews = completedReviews;
        this.pendingReviews = pendingReviews;
        this.averageRating = averageRating;
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