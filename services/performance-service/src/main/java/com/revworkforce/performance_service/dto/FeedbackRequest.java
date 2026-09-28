package com.revworkforce.performance_service.dto;

import jakarta.validation.constraints.NotBlank;

public class FeedbackRequest {

    @NotBlank
    private String feedback;

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}