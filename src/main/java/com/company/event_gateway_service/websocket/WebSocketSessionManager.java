package com.company.event_gateway_service.websocket;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketSession;

import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@Component
@Data
public class WebSocketSessionManager {

    private final AtomicReference<WebSocketSession> webSocketSession = new AtomicReference<>();


    public void registerSession(WebSocketSession session) {
        webSocketSession.set(session);
    }

    public void removeSession(WebSocketSession session) {
        if (webSocketSession.compareAndSet(session, null)) {
            log.info("Session removed successfully {}", session.getId());
        }

    }

    public boolean hasActiveSession() {
        WebSocketSession session = webSocketSession.get();
        return session != null && session.isOpen();
    }

    public void forceCloseCurrentSession() {
        WebSocketSession currentSession = webSocketSession.get();
        if (currentSession != null && currentSession.isOpen()) {
            log.info("Close previous session {}", currentSession.getId());
            currentSession.close().subscribe();
        }
    }
}
