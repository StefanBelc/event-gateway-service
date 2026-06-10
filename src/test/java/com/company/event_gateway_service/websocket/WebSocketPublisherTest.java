package com.company.event_gateway_service.websocket;

import com.company.event_gateway_service.service.NotificationService;
import com.company.event_gateway_service.websocket.handler.EventsWebSocketHandler;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import tools.jackson.databind.ObjectMapper;

import java.util.Objects;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class WebSocketPublisherTest {

    @Test
    void should_publish_payload_to_active_session() {
        WebSocketSession session = mock(WebSocketSession.class);
        WebSocketMessage message = mock(WebSocketMessage.class);
        WebSocketSessionManager webSocketSessionManager = new WebSocketSessionManager();
        ObjectMapper objectMapper = new ObjectMapper();
        NotificationService notificationService = mock(NotificationService.class);
        WebSocketPublisher webSocketPublisher = new WebSocketPublisher(notificationService, objectMapper);
        EventsWebSocketHandler webSocketHandler = new EventsWebSocketHandler(webSocketSessionManager, webSocketPublisher);
        when(session.isOpen()).thenReturn(true);
        when(session.textMessage("payload")).thenReturn(message);
        when(session.send(argThat(publisher -> publisher != null))).thenReturn(Mono.empty());
        when(webSocketPublisher.getPublishingFlux(session))
                .thenReturn(Flux.just(message));


        Mono<Void> result = webSocketHandler.handle(session);

        StepVerifier.create(result)
                .verifyComplete();

    }

    @Test
    void should_not_publish_payload_when_no_session_is_active() {
        WebSocketSession session = mock(WebSocketSession.class);
        WebSocketPublisher webSocketPublisher = mock(WebSocketPublisher.class);
        session.textMessage("payload");
        webSocketPublisher.getPublishingFlux(session);

        verify(session, never()).send(argThat(publisher -> publisher != null));
    }
}
