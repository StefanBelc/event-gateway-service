package com.company.event_gateway_service.kafka.subscriber;

import com.company.event_gateway_service.event.GameEvent;
import com.company.event_gateway_service.event.GameStatus;
import com.company.event_gateway_service.service.NotificationService;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class GameEventSubscriberTest {

    @Test
    void should_broadcast_game_event() {
        NotificationService notificationService = mock(NotificationService.class);
        GameEventSubscriber gameEventSubscriber = new GameEventSubscriber(notificationService);
        GameEvent gameEvent = GameEvent.builder()
                .gameId("game-1")
                .tournamentId("tournament-1")
                .status(GameStatus.CREATED)
                .duration(Duration.ZERO)
                .build();

        gameEventSubscriber.handleGameEvent(gameEvent);

        verify(notificationService).broadcastEvent(gameEvent);
    }
}
