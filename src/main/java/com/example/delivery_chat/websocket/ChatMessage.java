package com.example.delivery_chat.websocket;

public class ChatMessage {
    private Long deliveryId;
    private String senderType;
    private Long senderId;
    private String content;

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

    public void setDeliveryId(Long deliveryId) {
        this.deliveryId = deliveryId;
    }

    public void setSenderType(String senderType) {
        this.senderType = senderType;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public void setCotent(String content) {
        this.content = content;
    }
}
