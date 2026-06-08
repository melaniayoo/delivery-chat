package com.example.delivery_chat.service;

import com.example.delivery_chat.dto.MessageRequest;
import com.example.delivery_chat.entity.Message;
import com.example.delivery_chat.mapper.MessageMapper;
import org.springframework.stereotype.Service;

import java.util.List;

// Service layer for message-related business logic
@Service
public class MessageService {
    private final MessageMapper messageMapper;

    // Injects MessageMapper into the service
    public MessageService(MessageMapper messageMapper) {
        this.messageMapper = messageMapper;
    }

    // Retrieves all messages for specific delivery
    public List<Message> getMessagesByDeliveryId(Long deliveryId) {
        return messageMapper.findByDeliveryId(deliveryId);
    }

    public void sendMessage(Long deliveryId, MessageRequest request) {
        messageMapper.insertMessage(deliveryId, request.getSenderType(), request.getSenderId(), request.getContent());
    }
}
