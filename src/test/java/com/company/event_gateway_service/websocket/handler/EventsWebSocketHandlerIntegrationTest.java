package com.company.event_gateway_service.websocket.handler;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.net.URI;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EmbeddedKafka(partitions = 1)
class EventsWebSocketHandlerIntegrationTest {

    @LocalServerPort
    private int port;

    @Test
    void should_establish_websocket_connection() throws Exception {
        StandardWebSocketClient webSocketClient = new StandardWebSocketClient();
        URI url = URI.create("ws://localhost:" + port + "/ws/events");
        CompletableFuture<WebSocketSession> connectedSession = new CompletableFuture<>();

        webSocketClient.execute(new TextWebSocketHandler() {
            @Override
            public void afterConnectionEstablished(WebSocketSession session) {
                connectedSession.complete(session);
            }
        }, url.toString());

        WebSocketSession session = connectedSession.get(Duration.ofSeconds(5).toMillis(), TimeUnit.MILLISECONDS);

        assertThat(session.isOpen()).isTrue();
        session.close();
    }
}
