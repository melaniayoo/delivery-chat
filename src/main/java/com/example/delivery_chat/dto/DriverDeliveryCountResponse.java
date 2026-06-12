package com.example.delivery_chat.dto;

// 배송기사별 배송 개수를 응답할 때 사용하는 DTO
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
