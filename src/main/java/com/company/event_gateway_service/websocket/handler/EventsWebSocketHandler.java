package com.company.event_gateway_service.websocket.handler;

import com.company.event_gateway_service.websocket.WebSocketSessionManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;


@Component
@RequiredArgsConstructor
public class EventsWebSocketHandler implements WebSocketHandler {


    private final WebSocketSessionManager webSocketSessionManager;


    @Override
    public Mono<Void> handle(WebSocketSession session) {

        onEstablished(session);

        return session.receive()
                .doOnTerminate(() -> onClosed(session))
                .then();
    }

    public void onEstablished(WebSocketSession session) {
        if (webSocketSessionManager.hasActiveSession()) {
            webSocketSessionManager.removeSession(webSocketSessionManager.getWebSocketSession().get());
        }
        webSocketSessionManager.registerSession(session);
    }

    public void onClosed(WebSocketSession session) {
        webSocketSessionManager.removeSession(session);
    }
}
