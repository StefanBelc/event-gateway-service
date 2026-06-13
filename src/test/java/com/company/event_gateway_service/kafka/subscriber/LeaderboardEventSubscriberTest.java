package com.company.event_gateway_service.kafka.subscriber;

import com.company.event_gateway_service.event.LeaderboardEvent;
import com.company.event_gateway_service.service.NotificationService;
import com.company.event_gateway_service.websocket.WebSocketPublisher;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class LeaderboardEventSubscriberTest {

    @Test
    void should_broadcast_leaderboard_event() {
        NotificationService notificationService = mock(NotificationService.class);

        LeaderboardEventSubscriber leaderboardEventSubscriber = new LeaderboardEventSubscriber(notificationService);
        LeaderboardEvent leaderboardEvent = LeaderboardEvent.builder()
                .tournamentId("tournament-1")
                .timestamp(Timestamp.from(Instant.parse("2026-06-10T10:15:30Z")))
                .playersCount(3)
                .averageScore(12.5)
                .topPlayers(List.of())
                .build();

        leaderboardEventSubscriber.handleLeaderboardEvent(leaderboardEvent);

        verify(notificationService).broadcastLeaderboardEvent(leaderboardEvent);
    }
}
