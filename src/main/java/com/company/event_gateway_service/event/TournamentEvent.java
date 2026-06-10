package com.company.event_gateway_service.event;

import lombok.Builder;

import java.time.Duration;
import java.util.List;

@Builder
public record TournamentEvent(String tournamentId,
                              TournamentStatus tournamentStatus,
                              int matches,
                              int totalPlayers,
                              Duration totalDuration,
                              Duration avgMatchDuration,
                              List<GameEvent> gameResults) {
}
