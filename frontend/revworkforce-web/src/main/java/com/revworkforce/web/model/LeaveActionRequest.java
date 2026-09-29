package com.revworkforce.web.model;

public class LeaveActionRequest {

    private Long approverId;
    private String comments;

    public LeaveActionRequest() {
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