package com.company.event_gateway_service.websocket;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class WebSocketPublisherTest {

    @Test
    void should_publish_payload_to_active_session() throws IOException {
        WebSocketSession session = mock(WebSocketSession.class);
        WebSocketSessionManager webSocketSessionManager = new WebSocketSessionManager();
        ObjectMapper objectMapper = new ObjectMapper();
        WebSocketPublisher webSocketPublisher = new WebSocketPublisher(objectMapper, webSocketSessionManager);
        when(session.isOpen()).thenReturn(true);
        webSocketSessionManager.registerSession(session);
        ArgumentCaptor<TextMessage> messageCaptor = ArgumentCaptor.forClass(TextMessage.class);

        webSocketPublisher.publish(Map.of("type", "GAME_EVENT"));

        verify(session).sendMessage(messageCaptor.capture());
        assertThat(messageCaptor.getValue().getPayload()).contains("\"type\":\"GAME_EVENT\"");
    }

    @Test
    void should_not_publish_payload_when_no_session_is_active() throws IOException {
        WebSocketSession session = mock(WebSocketSession.class);
        WebSocketSessionManager webSocketSessionManager = new WebSocketSessionManager();
        ObjectMapper objectMapper = new ObjectMapper();
        WebSocketPublisher webSocketPublisher = new WebSocketPublisher(objectMapper, webSocketSessionManager);

        webSocketPublisher.publish(Map.of("type", "GAME_EVENT"));

        verify(session, never()).sendMessage(any(TextMessage.class));
    }
}
