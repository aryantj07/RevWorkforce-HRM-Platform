package com.revworkforce.leaveservice.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class LeaveActionRequestDto {

    @NotNull(message = "Approver ID is required")
    private Long approverId;

    @Size(max = 500, message = "Comments cannot exceed 500 characters")
    private String comments;

    public LeaveActionRequestDto() {
    }

    public LeaveActionRequestDto(Long approverId, String comments) {
        this.approverId = approverId;
        this.comments = comments;
    }

    public Long getApproverId() {
        return approverId;
    }

    public void setApproverId(Long approverId) {
        this.approverId = approverId;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
