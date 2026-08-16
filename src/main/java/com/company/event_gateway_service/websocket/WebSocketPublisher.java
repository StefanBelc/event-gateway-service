package com.company.event_gateway_service.websocket;

import com.company.event_gateway_service.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class WebSocketPublisher {

    private static final Logger log = LoggerFactory.getLogger(WebSocketPublisher.class);
    private final ObjectMapper objectMapper;
    private final WebSocketSessionManager webSocketSessionManager;


    public void publish(Object event) {
        if (webSocketSessionManager.hasActiveSession()) {
            String formattedEvent = objectMapper.writeValueAsString(event);
            WebSocketSession activeSession = webSocketSessionManager.getWebSocketSession().get();
            try {
                activeSession.sendMessage(
                        new TextMessage(formattedEvent)
                );
                log.info("Event published successfully !");
            } catch (IOException e) {
                log.error("Could not publish event {}" , e.getMessage());
            }
        }
    }
}

