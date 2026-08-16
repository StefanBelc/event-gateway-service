package com.company.event_gateway_service.kafka.subscriber;

import com.company.event_gateway_service.service.NotificationService;
import com.company.event_gateway_service.event.TournamentEvent;
import com.company.event_gateway_service.websocket.WebSocketPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TournamentEventSubscriber {

    private final NotificationService notificationService;

    @KafkaListener(topics = "${myapp.kafka.topics.tournament-events}", groupId = "gateway-service")
    public void handleTournamentEvent(TournamentEvent tournamentEvent) {
        log.info("Tournament event received {}" , tournamentEvent);
        notificationService.broadcastTournamentEvent(tournamentEvent);
    }
}
