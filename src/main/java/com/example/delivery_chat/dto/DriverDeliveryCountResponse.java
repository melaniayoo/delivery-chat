package com.example.delivery_chat.dto;

public class DriverDeliveryCountResponse {
    private Long driverId;
    private Long deliveryCount;

    public Long getDriverId() {
        return driverId;
    }

    public Long getDeliveryCount() {
        return deliveryCount;
    }

    public void setDriverId(Long driverId) {
        this.driverId = driverId;
    }

    public void setDeliveryCount(Long deliveryCount) {
        this.deliveryCount = deliveryCount;
    }
}
