package com.example.delivery_chat.dto;

// 배송 상세 정보 응답
public class DeliveryDetailResponse {
    private Long deliveryId;
    private String customerName;
    private String customerPhoneNumber;
    private String driverName;
    private String driverPhoneNumber;
    private String deliveryAddress;
    private String status;

    public Long getDeliveryId() {
        return deliveryId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerPhoneNumber() {
        return customerPhoneNumber;
    }

    public String getDriverName() {
        return driverName;
    }

    public String getDriverPhoneNumber() {
        return driverPhoneNumber;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public String getStatus() {
        return status;
    }

    public void setDeliveryId(Long deliveryId) {
        this.deliveryId = deliveryId;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setCustomerPhoneNumber(String customerPhoneNumber) {
        this.customerPhoneNumber = customerPhoneNumber; 
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public void setDriverPhonenumber(String driverPhoneNumber) {
        this.driverPhoneNumber = driverPhoneNumber;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
