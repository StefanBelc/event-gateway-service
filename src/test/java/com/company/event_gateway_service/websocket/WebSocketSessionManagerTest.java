package com.company.event_gateway_service.websocket;

import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class WebSocketSessionManagerTest {

    @Test
    void should_register_session() {
        WebSocketSession session = mock(WebSocketSession.class);
        WebSocketSessionManager webSocketSessionManager = new WebSocketSessionManager();

        webSocketSessionManager.registerSession(session);

        assertThat(webSocketSessionManager.getWebSocketSession().get()).isSameAs(session);
    }

    @Test
    void should_report_active_session_when_registered_session_is_open() {
        WebSocketSession session = mock(WebSocketSession.class);
        when(session.isOpen()).thenReturn(true);
        WebSocketSessionManager webSocketSessionManager = new WebSocketSessionManager();

        webSocketSessionManager.registerSession(session);

        assertThat(webSocketSessionManager.hasActiveSession()).isTrue();
    }

    @Test
    void should_report_no_active_session_when_no_session_is_registered() {
        WebSocketSessionManager webSocketSessionManager = new WebSocketSessionManager();

        assertThat(webSocketSessionManager.hasActiveSession()).isFalse();
    }

    @Test
    void should_close_and_clear_registered_session() {
        WebSocketSession session = mock(WebSocketSession.class);
        when(session.close()).thenReturn(Mono.empty());
        WebSocketSessionManager webSocketSessionManager = new WebSocketSessionManager();
        webSocketSessionManager.registerSession(session);

        webSocketSessionManager.removeSession(session);


        assertThat(webSocketSessionManager.getWebSocketSession().get()).isNull();
        assertThat(webSocketSessionManager.hasActiveSession()).isFalse();
    }
}
