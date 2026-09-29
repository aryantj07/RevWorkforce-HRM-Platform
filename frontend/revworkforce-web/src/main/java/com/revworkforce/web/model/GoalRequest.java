package com.revworkforce.web.model;

public class GoalRequest {

    private Long employeeId;
    private String title;
    private String description;

    public GoalRequest() {
    }

    public GoalRequest(Long employeeId, String title, String description) {
        this.employeeId = employeeId;
        this.title = title;
        this.description = description;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}