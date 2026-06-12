package com.example.delivery_chat.dto;

// 현재 로그인 사용자 정보 응답
public class AuthMeResponse {
    private String username;
    private String role;
    private Long customerId;
    private Long driverId;

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public Long getDriverId() {
        return driverId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public void setDriverId(Long driverId) {
        this.driverId = driverId;
    }
}
