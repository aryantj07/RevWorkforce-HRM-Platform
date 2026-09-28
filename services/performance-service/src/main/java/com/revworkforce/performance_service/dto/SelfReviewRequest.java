package com.revworkforce.performance_service.dto;

import jakarta.validation.constraints.NotBlank;

public class SelfReviewRequest {

    @NotBlank
    private String selfReview;

    public String getSelfReview() {
        return selfReview;
    }

    public void setSelfReview(String selfReview) {
        this.selfReview = selfReview;
    }
}