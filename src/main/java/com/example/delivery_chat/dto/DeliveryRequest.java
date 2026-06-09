package com.example.delivery_chat.dto;

// Request DTO used when creating a new delivery
public class DeliveryRequest {
    private Long customerId;
    private String deliveryAddress;

    public Long getCustomerId() {
        return customerId;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }
}
