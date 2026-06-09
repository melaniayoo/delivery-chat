package com.example.delivery_chat.websocket;

import org.springframework.stereotype.Controller;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;

import com.example.delivery_chat.service.MessageService;
import com.example.delivery_chat.dto.MessageRequest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// Handles WebSocket chat messages
@Controller
public class ChatController {
    private final MessageService messageService;
    private final SimpMessagingTemplate messagingTemplate;

    public ChatController(MessageService messageService, SimpMessagingTemplate messagingTemplate) {
        this.messageService = messageService;
        this.messagingTemplate = messagingTemplate;
    }

    // Receives messages sent to /app/chat.send
    @MessageMapping("/chat.send")
    public void sendMessage(ChatMessage chatMessage) {
        System.out.println("Received from WebSocket: " + chatMessage.getContent());

        MessageRequest request = new MessageRequest();
        request.setSenderType(chatMessage.getSenderType());
        request.setSenderId(chatMessage.getSenderId());
        request.setContent(chatMessage.getContent());

        // Save message to database
        messageService.sendMessage(chatMessage.getDeliveryId(), request);

        // Add created time for real-time WebSocket display 
        String createdAt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")); 
        chatMessage.setCreatedAt(createdAt);

        String topic = "/topic/deliveries/" + chatMessage.getDeliveryId();
        System.out.println("Broadcasting to: " + topic);
        // Send message to all clients subscribed to this delivery chat room 
        messagingTemplate.convertAndSend(topic, chatMessage);
    }
}
