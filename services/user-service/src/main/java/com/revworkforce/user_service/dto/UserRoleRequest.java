package com.revworkforce.user_service.dto;

import jakarta.validation.constraints.NotBlank;

public class UserRoleRequest {

    @NotBlank(message = "Role is required")
    private String role;

    public UserRoleRequest() {
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}