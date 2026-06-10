package com.company.event_gateway_service.websocket.handler;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient;
import org.springframework.web.reactive.socket.client.WebSocketClient;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EmbeddedKafka(partitions = 1)
class EventsWebSocketHandlerIntegrationTest {

    @LocalServerPort
    private int port;

    @Test
    void should_establish_websocket_connection() {
        WebSocketClient webSocketClient = new ReactorNettyWebSocketClient();
        URI url = URI.create("ws://localhost:" + port + "/ws/events");

        webSocketClient.execute(url, session -> {
            assertThat(session.isOpen()).isTrue();
            return Mono.empty();
        }).block(Duration.ofSeconds(5));
    }
}
