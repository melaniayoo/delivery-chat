package com.example.delivery_chat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;

import com.example.delivery_chat.service.MessageService;
import com.example.delivery_chat.dto.ChatMessage;
import com.example.delivery_chat.dto.MessageRequest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// Handles WebSocket chat messages
@Controller
public class ChatController {
    // 메세지를 DB에 저장하는 로직을 담당
    private final MessageService messageService;
    // 서버가 특정 topic을 구독 중인 클라이언트들에게 메세지를 보낼 때 사용
    private final SimpMessagingTemplate messagingTemplate;

    public ChatController(MessageService messageService, SimpMessagingTemplate messagingTemplate) {
        this.messageService = messageService;
        this.messagingTemplate = messagingTemplate;
    }

    // Receives messages sent to /app/chat.send
    @MessageMapping("/chat.send")
    public void sendMessage(ChatMessage chatMessage) {
        System.out.println("Received from WebSocket: " + chatMessage.getContent());

        // MessageService에 넘기기 위해 MessageRequest 생성
        MessageRequest request = new MessageRequest();
        request.setSenderType(chatMessage.getSenderType());
        request.setSenderId(chatMessage.getSenderId());
        request.setContent(chatMessage.getContent());

        // Save message to database
        messageService.sendMessage(chatMessage.getDeliveryId(), request);

        // Add created time for real-time WebSocket display 
        String createdAt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")); 
        chatMessage.setCreatedAt(createdAt);

        // 이 메세지를 보낼 topic 주소 생성, 배송 ID별로 채팅방을 나누기 위해 /topic/deliveries/{deliveryId} 사용
        String topic = "/topic/deliveries/" + chatMessage.getDeliveryId();
        System.out.println("Broadcasting to: " + topic);
        // Send message to all clients subscribed to this delivery chat room 
        messagingTemplate.convertAndSend(topic, chatMessage);
    }
}
