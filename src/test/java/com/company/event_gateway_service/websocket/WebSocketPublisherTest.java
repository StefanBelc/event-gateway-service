package com.company.event_gateway_service.websocket;

import org.junit.jupiter.api.Test;
import org.reactivestreams.Publisher;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;

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
        WebSocketPublisher webSocketPublisher = new WebSocketPublisher(webSocketSessionManager);
        when(session.isOpen()).thenReturn(true);
        when(session.textMessage("payload")).thenReturn(message);
        when(session.send(argThat(publisher -> publisher != null))).thenReturn(Mono.empty());
        webSocketSessionManager.registerSession(session);

        webSocketPublisher.publishToTopic("payload");

        verify(session).textMessage("payload");
        verify(session).send(argThat(publisher -> publisher != null));
    }

    @Test
    void should_not_publish_payload_when_no_session_is_active() {
        WebSocketSession session = mock(WebSocketSession.class);
        WebSocketSessionManager webSocketSessionManager = new WebSocketSessionManager();
        WebSocketPublisher webSocketPublisher = new WebSocketPublisher(webSocketSessionManager);

        webSocketPublisher.publishToTopic("payload");

        verify(session, never()).send(argThat(publisher -> publisher != null));
    }
}
