package com.example.delivery_chat.dto;

// 배송 상태를 변경할 때 브라우저가 서버로 보내는 데이터
public class DeliveryStatusUpdateRequest {
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
