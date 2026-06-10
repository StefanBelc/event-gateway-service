package com.company.event_gateway_service.kafka.subscriber;

import com.company.event_gateway_service.service.NotificationService;
import com.company.event_gateway_service.event.TournamentEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TournamentEventSubscriber {

    private final NotificationService notificationService;

    @KafkaListener(topics = "${myapp.kafka.topics.tournament-events}", groupId = "gateway-service")
    public void handleTournamentEvent(TournamentEvent tournamentEvent) {
        notificationService.broadcastEvent(tournamentEvent);

    }
}
