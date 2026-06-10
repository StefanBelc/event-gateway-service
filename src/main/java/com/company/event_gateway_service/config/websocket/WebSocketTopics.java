package com.company.event_gateway_service.config.websocket;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Data
public class WebSocketTopics {

    public final static String WS_TOPIC_LEADERBOARD = "/topic/leaderboard";
    public final static String WS_TOPIC_TOURNAMENT = "/topic/tournament";
    public final static String WS_TOPIC_EVENTS = "/topic/events";


}
