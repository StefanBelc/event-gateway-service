package com.company.event_gateway_service.kafka.subscriber;


import com.company.event_gateway_service.event.LeaderboardEvent;
import com.company.event_gateway_service.service.NotificationService;
import com.company.event_gateway_service.websocket.WebSocketPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class LeaderboardEventSubscriber {

    private final NotificationService notificationService;


    @KafkaListener(topics = "${myapp.kafka.topics.leaderboard-events}", groupId = "gateway-service")
    public void handleLeaderboardEvent(LeaderboardEvent leaderboardEvent) {
        log.info("Leaderboard event received {}" , leaderboardEvent);
        notificationService.broadcastLeaderboardEvent(leaderboardEvent);
    }
}
