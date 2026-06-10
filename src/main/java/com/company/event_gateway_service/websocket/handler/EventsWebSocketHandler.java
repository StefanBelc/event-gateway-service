package com.company.event_gateway_service.websocket.handler;

import com.company.event_gateway_service.websocket.WebSocketPublisher;
import com.company.event_gateway_service.websocket.WebSocketSessionManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Slf4j
@Component
@RequiredArgsConstructor
public class EventsWebSocketHandler implements WebSocketHandler {


    private final WebSocketSessionManager webSocketSessionManager;
    private final WebSocketPublisher webSocketPublisher;


    @Override
    public Mono<Void> handle(WebSocketSession session) {
        onEstablished(session);

        Flux<WebSocketMessage> eventsFlux = webSocketPublisher.getPublishingFlux(session);

        return session.send(eventsFlux)
                .doFinally(signalType -> {
                    onClosed(session);
                    log.info("Session closed: {}", session.getId());
                });

    }

    public void onEstablished(WebSocketSession session) {
        if (webSocketSessionManager.hasActiveSession()) {
            webSocketSessionManager.forceCloseCurrentSession();
        }
        webSocketSessionManager.registerSession(session);
    }

    public void onClosed(WebSocketSession session) {
        webSocketSessionManager.removeSession(session);
    }
}
