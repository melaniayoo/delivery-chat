package com.example.delivery_chat.controller;

import com.example.delivery_chat.entity.Message;
import com.example.delivery_chat.service.MessageService;
import com.example.delivery_chat.dto.MessageRequest;

import java.util.List;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


// REST controller for handling message-related API requests
@RestController
public class MessageController {
    private final MessageService messageService;

    // Injects MessageService into the controller 
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    // Handles GET requests to /deliveries/{deliveryId}/messages
    // 특정 배송의 메시지 목록 조회 
    @GetMapping("/deliveries/{deliveryId}/messages")
    public List<Message> getMessagesByDeliveryId(@PathVariable Long deliveryId) {
        return messageService.getMessagesByDeliveryId(deliveryId);
    }

    // 특정 배송에 메세지를 전송
    @PostMapping("/deliveries/{deliveryId}/messages")
    public String sendMessage(@PathVariable Long deliveryId, @RequestBody MessageRequest request) {
        messageService.sendMessage(deliveryId, request);
        return "Message sent successfully.";
    }
}
