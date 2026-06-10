package com.company.event_gateway_service.service;

import com.company.event_gateway_service.event.GameEvent;
import com.company.event_gateway_service.event.GameStatus;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

class NotificationServiceTest {

    @Test
    void should_serialize_event_payload() {
        NotificationService notificationService = new NotificationService();
        GameEvent gameEvent = GameEvent.builder()
                .gameId("game-1")
                .tournamentId("tournament-1")
                .status(GameStatus.FINISHED)
                .player1("Ana")
                .player2("Mihai")
                .winner("Ana")
                .loser("Mihai")
                .draw(false)
                .duration(Duration.ofSeconds(12))
                .build();

        Object payload = notificationService.broadcastEvent(gameEvent);

        assertThat(payload.toString())
                .contains("\"gameId\":\"game-1\"")
                .contains("\"tournamentId\":\"tournament-1\"")
                .contains("\"status\":\"FINISHED\"")
                .contains("\"winner\":\"Ana\"");
    }
}
