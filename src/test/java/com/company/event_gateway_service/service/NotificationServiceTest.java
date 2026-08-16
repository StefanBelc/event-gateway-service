package com.company.event_gateway_service.service;

import com.company.event_gateway_service.event.GameEvent;
import com.company.event_gateway_service.event.GameStatus;
import com.company.event_gateway_service.event.LeaderboardEvent;
import com.company.event_gateway_service.event.TournamentEvent;
import com.company.event_gateway_service.event.TournamentStatus;
import com.company.event_gateway_service.websocket.WebSocketPublisher;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class NotificationServiceTest {

    @Test
    void should_broadcast_game_event() {
        WebSocketPublisher webSocketPublisher = mock(WebSocketPublisher.class);
        NotificationService notificationService = new NotificationService(webSocketPublisher);
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

        notificationService.broadcastGameEvent(gameEvent);

        verify(webSocketPublisher).publish(gameEvent);
    }

    @Test
    void should_broadcast_leaderboard_event() {
        WebSocketPublisher webSocketPublisher = mock(WebSocketPublisher.class);
        NotificationService notificationService = new NotificationService(webSocketPublisher);
        LeaderboardEvent leaderboardEvent = LeaderboardEvent.builder()
                .tournamentId("tournament-1")
                .timestamp(Instant.parse("2026-06-11T08:00:00Z"))
                .playersCount(3)
                .averageScore(10.5)
                .topPlayers(List.of())
                .build();

        notificationService.broadcastLeaderboardEvent(leaderboardEvent);

        verify(webSocketPublisher).publish(leaderboardEvent);
    }

    @Test
    void should_broadcast_tournament_event() {
        WebSocketPublisher webSocketPublisher = mock(WebSocketPublisher.class);
        NotificationService notificationService = new NotificationService(webSocketPublisher);
        TournamentEvent tournamentEvent = TournamentEvent.builder()
                .tournamentId("tournament-1")
                .tournamentStatus(TournamentStatus.STARTED)
                .matches(6)
                .totalPlayers(4)
                .totalDuration(Duration.ZERO)
                .avgMatchDuration(Duration.ZERO)
                .gameResults(List.of())
                .build();

        notificationService.broadcastTournamentEvent(tournamentEvent);

        verify(webSocketPublisher).publish(tournamentEvent);
    }
}
