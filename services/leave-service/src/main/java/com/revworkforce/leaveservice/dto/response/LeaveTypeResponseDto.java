package com.revworkforce.leaveservice.dto.response;

public class LeaveTypeResponseDto {

    private Long id;
    private String name;
    private String code;
    private String description;
    private boolean isPaid;
    private boolean isActive;

    public LeaveTypeResponseDto() {
    }

    public LeaveTypeResponseDto(Long id, String name, String code, String description, boolean isPaid, boolean isActive) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.description = description;
        this.isPaid = isPaid;
        this.isActive = isActive;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
