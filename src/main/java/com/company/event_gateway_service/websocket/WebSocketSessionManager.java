package com.company.event_gateway_service.websocket;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
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

    public void forceCloseCurrentSession()  {
        WebSocketSession currentSession = webSocketSession.get();
        if (currentSession != null && currentSession.isOpen()) {
            log.info("Closing previous session {}", currentSession.getId());
            try {
                currentSession.close();
            } catch (IOException e) {
                log.warn("Failed to close session {}", currentSession.getId(), e);
            }
        }
    }
}
