package com.company.event_gateway_service.service;

import com.company.event_gateway_service.event.GameEvent;
import com.company.event_gateway_service.event.GameStatus;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

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

        Flux<GameEvent> stream = notificationService.getGameEventStream();
        StepVerifier.create(stream).
                then(() -> {
                    notificationService.broadcastGameEvent(gameEvent);
                })
                .assertNext(receivedEvent -> {
                    assertThat(receivedEvent.gameId()).isEqualTo("game-1");
                    assertThat(receivedEvent.tournamentId()).isEqualTo("tournament-1");
                    assertThat(receivedEvent.status()).isEqualTo(GameStatus.FINISHED);
                    assertThat(receivedEvent.winner()).isEqualTo("Ana");
                })
                .thenCancel()
                .verify();
    }
}
