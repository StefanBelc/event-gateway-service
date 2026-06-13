package com.company.event_gateway_service.service;


import com.company.event_gateway_service.event.GameEvent;
import com.company.event_gateway_service.event.LeaderboardEvent;
import com.company.event_gateway_service.event.TournamentEvent;
import com.company.event_gateway_service.websocket.WebSocketPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final WebSocketPublisher webSocketPublisher;

    public void broadcastGameEvent(GameEvent gameEvent) {
        webSocketPublisher.publish(gameEvent);
    }

    public void broadcastLeaderboardEvent(LeaderboardEvent leaderboardEvent) {
        webSocketPublisher.publish(leaderboardEvent);
    }

    public void broadcastTournamentEvent(TournamentEvent tournamentEvent) {
        webSocketPublisher.publish(tournamentEvent);
    }
}
