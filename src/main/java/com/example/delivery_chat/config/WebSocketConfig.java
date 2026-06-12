package com.example.delivery_chat.config;

import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

// Configures WebSocket/STOMP communication for real-time chat.
// 실시간 채팅을 위해 클라이언트가 어디로 연결하고, 메세지를 어떤 경로로 주고받을지 설정
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    // Defines the WebSocket connection endpoint. 
    // WebSocket 연결 시작 주소를 설정, 클라이언트는 /ws 주소로 WebSocket 연결을 시작
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns("*")
                .withSockJS();
    }
    // Defines message routing rules. 
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 서버가 클라이언트들에게 메세지를 보낼 때 사용하는 prefix (/topic/deliveries/{deliveryId})를 구독한 사용자들에게 메세지 전달
        registry.enableSimpleBroker("/topic");       // Server sends messages to clients
        // 클라이언트가 서버로 메세지를 보낼 때 사용하는 prefix /app/chat.send
        registry.setApplicationDestinationPrefixes("/app");     // Clients sends messages to server 
    }
}
