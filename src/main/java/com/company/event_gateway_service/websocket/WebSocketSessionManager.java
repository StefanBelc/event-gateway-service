package com.company.event_gateway_service.websocket;

import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketSession;

import java.util.concurrent.atomic.AtomicReference;

@Component
@Data
public class WebSocketSessionManager {

    private final AtomicReference<WebSocketSession> webSocketSession = new AtomicReference<>();


    public void registerSession(WebSocketSession session) {
        webSocketSession.set(session);
    }

    public void removeSession(WebSocketSession session) {
        WebSocketSession currentSession = webSocketSession.get();
        if (currentSession != null && currentSession.equals(session)) {
            currentSession.close().subscribe();
            webSocketSession.compareAndSet(currentSession, null);
        }
    }

    public boolean hasActiveSession() {
        WebSocketSession session = webSocketSession.get();
        return session != null && session.isOpen();
    }
}
