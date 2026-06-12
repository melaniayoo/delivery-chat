package com.example.delivery_chat.entity;

// 로그인 계정 정보를 담는 클래스
public class AppUser {
    private Long id;
    private String username;
    private String password;
    private String role;
    private Long customerId;
    private Long driverId;

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
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

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
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
