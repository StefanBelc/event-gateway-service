package com.company.event_gateway_service.event;

import lombok.Builder;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Builder
public record LeaderboardEvent(String tournamentId,
                               Instant timestamp,
                               int playersCount,
                               double averageScore,
                               List<TopPlayer> topPlayers) {
}
