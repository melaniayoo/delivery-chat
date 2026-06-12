package com.example.delivery_chat.entity;

// 메세지 정보를 담은 클래스 
public class Message {
    private Long id;
    private Long deliveryId;
    private String senderType;
    private Long senderId;
    private String content;
    private String createdAt;

    public Long getId() {
        return id;
    }

    public Long getDeliveryId() {
        return deliveryId;
    }

    public String getSenderType() {
        return senderType;
    }

    public Long getSenderId() {
        return senderId;
    }

    public String getContent() {
        return content;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDeliveryId(Long deliveryId) {
        this.deliveryId = deliveryId;
    }

    public void setSenderType(String senderType) {
        this.senderType = senderType;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
