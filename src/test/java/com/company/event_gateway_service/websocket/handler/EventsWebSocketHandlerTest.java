package com.company.event_gateway_service.websocket.handler;

import com.company.event_gateway_service.websocket.WebSocketPublisher;
import com.company.event_gateway_service.websocket.WebSocketSessionManager;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class EventsWebSocketHandlerTest {

    @Test
    void should_register_session_when_connection_is_established() {
        WebSocketSession session = mock(WebSocketSession.class);
        WebSocketSessionManager webSocketSessionManager = new WebSocketSessionManager();
        WebSocketPublisher webSocketPublisher = mock(WebSocketPublisher.class);
        EventsWebSocketHandler eventsWebSocketHandler = new EventsWebSocketHandler(webSocketSessionManager, webSocketPublisher);

        eventsWebSocketHandler.onEstablished(session);

        assertThat(webSocketSessionManager.getWebSocketSession().get()).isSameAs(session);
    }

    @Test
    void should_close_existing_session_before_registering_new_session() {
        WebSocketSession existingSession = mock(WebSocketSession.class);
        WebSocketSession newSession = mock(WebSocketSession.class);
        WebSocketPublisher webSocketPublisher = mock(WebSocketPublisher.class);
        WebSocketSessionManager webSocketSessionManager = new WebSocketSessionManager();
        EventsWebSocketHandler eventsWebSocketHandler = new EventsWebSocketHandler(webSocketSessionManager, webSocketPublisher);
        when(existingSession.isOpen()).thenReturn(true);
        when(existingSession.close()).thenReturn(Mono.empty());
        webSocketSessionManager.registerSession(existingSession);

        eventsWebSocketHandler.onEstablished(newSession);

        verify(existingSession).close();
        assertThat(webSocketSessionManager.getWebSocketSession().get()).isSameAs(newSession);
    }

    @Test
    void should_remove_session_when_connection_is_closed() {

        WebSocketSession session = mock(WebSocketSession.class);
        WebSocketSessionManager webSocketSessionManager = new WebSocketSessionManager();
        WebSocketPublisher webSocketPublisher = mock(WebSocketPublisher.class);
        EventsWebSocketHandler eventsWebSocketHandler = new EventsWebSocketHandler(webSocketSessionManager, webSocketPublisher);

        webSocketSessionManager.registerSession(session);
        eventsWebSocketHandler.onClosed(session);

        assertThat(webSocketSessionManager.getWebSocketSession().get()).isNull();
    }
}
