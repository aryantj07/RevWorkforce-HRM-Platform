package com.revworkforce.performance_service.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class RatingCreateRequest {

    @NotNull
    private Long reviewId;

    @NotNull
    private Long managerId;

    @NotNull
    @Min(1)
    @Max(5)
    private Integer rating;

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

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }
}