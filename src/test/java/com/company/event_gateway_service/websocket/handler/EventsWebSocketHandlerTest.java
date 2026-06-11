package com.company.event_gateway_service.websocket.handler;

import com.company.event_gateway_service.websocket.WebSocketPublisher;
import com.company.event_gateway_service.websocket.WebSocketSessionManager;
import org.junit.jupiter.api.Test;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class EventsWebSocketHandlerTest {

    @Test
    void should_register_session_when_connection_is_established() {
        WebSocketSession session = mock(WebSocketSession.class);
        WebSocketSessionManager webSocketSessionManager = new WebSocketSessionManager();
        EventsWebSocketHandler eventsWebSocketHandler = new EventsWebSocketHandler(webSocketSessionManager);

        eventsWebSocketHandler.afterConnectionEstablished(session);

        assertThat(webSocketSessionManager.getWebSocketSession().get()).isSameAs(session);
    }

    @Test
    void should_close_existing_session_before_registering_new_session() throws IOException {
        WebSocketSession existingSession = mock(WebSocketSession.class);
        WebSocketSession newSession = mock(WebSocketSession.class);

        WebSocketSessionManager webSocketSessionManager = new WebSocketSessionManager();
        EventsWebSocketHandler eventsWebSocketHandler = new EventsWebSocketHandler(webSocketSessionManager);
        when(existingSession.isOpen()).thenReturn(true);
        webSocketSessionManager.registerSession(existingSession);

        eventsWebSocketHandler.afterConnectionEstablished(newSession);

        verify(existingSession).close();
        assertThat(webSocketSessionManager.getWebSocketSession().get()).isSameAs(newSession);
    }

    @Test
    void should_remove_session_when_connection_is_closed() {

        WebSocketSession session = mock(WebSocketSession.class);
        WebSocketSessionManager webSocketSessionManager = new WebSocketSessionManager();
        EventsWebSocketHandler eventsWebSocketHandler = new EventsWebSocketHandler(webSocketSessionManager);

        webSocketSessionManager.registerSession(session);
        eventsWebSocketHandler.onClosed(session);

        assertThat(webSocketSessionManager.getWebSocketSession().get()).isNull();
    }

    @Test
    void should_remove_session_when_connection_is_closed_by_spring() {

        WebSocketSession session = mock(WebSocketSession.class);
        WebSocketSessionManager webSocketSessionManager = new WebSocketSessionManager();
        EventsWebSocketHandler eventsWebSocketHandler = new EventsWebSocketHandler(webSocketSessionManager);

        webSocketSessionManager.registerSession(session);
        eventsWebSocketHandler.afterConnectionClosed(session, CloseStatus.NORMAL);

        assertThat(webSocketSessionManager.getWebSocketSession().get()).isNull();
    }
}
