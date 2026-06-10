package com.company.event_gateway_service.websocket;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class WebSocketPublisher {

    private final WebSocketSessionManager webSocketSessionManager;


    public void publishToTopic(String payload) {
        if (webSocketSessionManager.hasActiveSession()) {

            WebSocketSession session = webSocketSessionManager.getWebSocketSession().get();
            session.send(Mono.just(session.textMessage(payload)))
                    .subscribe();
        }
    }
}
