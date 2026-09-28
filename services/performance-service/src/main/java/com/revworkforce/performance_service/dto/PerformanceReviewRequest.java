package com.revworkforce.performance_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class PerformanceReviewRequest {

    @NotNull
    private Long employeeId;

    @NotNull
    private Long managerId;

    @NotBlank
    private String reviewPeriod;

    @NotBlank
    private String selfReview;

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public Long getManagerId() {
        return managerId;
    }

    public void setManagerId(Long managerId) {
        this.managerId = managerId;
    }

    public String getReviewPeriod() {
        return reviewPeriod;
    }

    public void setReviewPeriod(String reviewPeriod) {
        this.reviewPeriod = reviewPeriod;
    }

    public String getSelfReview() {
        return selfReview;
    }

    public void setSelfReview(String selfReview) {
        this.selfReview = selfReview;
    }
}