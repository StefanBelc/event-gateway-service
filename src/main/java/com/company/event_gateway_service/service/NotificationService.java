package com.company.event_gateway_service.service;


import com.company.event_gateway_service.event.GameEvent;
import com.company.event_gateway_service.event.LeaderboardEvent;
import com.company.event_gateway_service.event.TournamentEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import tools.jackson.databind.ObjectMapper;

@Service
@RequiredArgsConstructor
public class NotificationService {


    private final Sinks.Many<GameEvent> gameEventSink = Sinks.many().multicast().directBestEffort();
    private final Sinks.Many<TournamentEvent> tournamentEventSink = Sinks.many().multicast().directBestEffort();
    private final Sinks.Many<LeaderboardEvent> leaderboardEventSink = Sinks.many().multicast().directBestEffort();


    public void broadcastGameEvent(GameEvent event) {
        gameEventSink.tryEmitNext(event);
    }

    public void broadcastTournamentEvent(TournamentEvent event) {
        tournamentEventSink.tryEmitNext(event);
    }

    public void broadcastLeaderboardEvent(LeaderboardEvent event) {
        leaderboardEventSink.tryEmitNext(event);
    }


    public Flux<GameEvent> getGameEventStream() { return gameEventSink.asFlux(); }
    public Flux<TournamentEvent> getTournamentEventStream() { return tournamentEventSink.asFlux(); }
    public Flux<LeaderboardEvent> getLeaderboardEventStream() { return leaderboardEventSink.asFlux(); }
}
