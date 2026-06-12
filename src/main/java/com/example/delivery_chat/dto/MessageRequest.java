package com.example.delivery_chat.dto;

// 메세지 전송 데이터
public class MessageRequest {
    private String senderType;
    private Long senderId;
    private String content;

    public String getSenderType() {
        return senderType;
    }

    public Long getSenderId() {
        return senderId;
    }

    public String getContent() {
        return content;
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
}
