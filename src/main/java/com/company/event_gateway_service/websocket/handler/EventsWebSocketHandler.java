package com.company.event_gateway_service.websocket.handler;

import com.company.event_gateway_service.websocket.WebSocketPublisher;
import com.company.event_gateway_service.websocket.WebSocketSessionManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;


@Slf4j
@Component
@RequiredArgsConstructor
public class EventsWebSocketHandler extends TextWebSocketHandler {


    private final WebSocketSessionManager webSocketSessionManager;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        if (webSocketSessionManager.hasActiveSession()) {
            webSocketSessionManager.forceCloseCurrentSession();
        }
        webSocketSessionManager.registerSession(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) {
        webSocketSessionManager.removeSession(session);
    }
}
