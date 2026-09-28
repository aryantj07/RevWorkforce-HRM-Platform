package com.revworkforce.performance_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class FeedbackCreateRequest {

    @NotNull
    private Long reviewId;

    @NotNull
    private Long managerId;

    @NotBlank
    private String comments;

    public Long getReviewId() {
        return reviewId;
    }

    public void setReviewId(Long reviewId) {
        this.reviewId = reviewId;
    }

    public Long getManagerId() {
        return managerId;
    }

    public void setManagerId(Long managerId) {
        this.managerId = managerId;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}