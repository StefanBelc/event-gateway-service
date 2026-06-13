package com.company.event_gateway_service.event;

import lombok.Builder;

@Builder
public record TopPlayer(int rank, String playerName, int score) {
}
