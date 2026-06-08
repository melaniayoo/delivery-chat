package com.example.delivery_chat.entity;

// Represents a delivery record from deliveries table
public class Delivery {
    private Long id;
    private Long customerId;
    private Long driverId;
    private String deliveryAddress;
    private String status;

    public Long getId() {
        return id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public Long getDriverId() {
        return driverId;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public String getStatus() {
        return status;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public void setDriverId(Long driverId) {
        this.driverId = driverId;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
