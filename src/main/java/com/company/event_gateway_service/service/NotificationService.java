package com.company.event_gateway_service.service;


import com.company.event_gateway_service.event.GameEvent;
import com.company.event_gateway_service.event.LeaderboardEvent;
import com.company.event_gateway_service.event.TournamentEvent;
import com.company.event_gateway_service.websocket.WebSocketPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {

    private final WebSocketPublisher webSocketPublisher;

    public void broadcastGameEvent(GameEvent gameEvent) {
        log.info("Game event published {}" , gameEvent);
        webSocketPublisher.publish(gameEvent);
    }

    public void broadcastLeaderboardEvent(LeaderboardEvent leaderboardEvent) {
        log.info("Leaderboard event published {}" , leaderboardEvent);
        webSocketPublisher.publish(leaderboardEvent);
    }

    public void broadcastTournamentEvent(TournamentEvent tournamentEvent) {
        log.info("Tournament event published {}" , tournamentEvent);
        webSocketPublisher.publish(tournamentEvent);
    }
}
