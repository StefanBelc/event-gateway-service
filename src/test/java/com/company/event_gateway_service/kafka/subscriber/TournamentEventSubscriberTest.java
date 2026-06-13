package com.company.event_gateway_service.kafka.subscriber;

import com.company.event_gateway_service.event.TournamentEvent;
import com.company.event_gateway_service.event.TournamentStatus;
import com.company.event_gateway_service.service.NotificationService;
import com.company.event_gateway_service.websocket.WebSocketPublisher;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class TournamentEventSubscriberTest {

    @Test
    void should_broadcast_tournament_event() {
        NotificationService notificationService = mock(NotificationService.class);

        TournamentEventSubscriber tournamentEventSubscriber = new TournamentEventSubscriber(notificationService);
        TournamentEvent tournamentEvent = TournamentEvent.builder()
                .tournamentId("tournament-1")
                .tournamentStatus(TournamentStatus.STARTED)
                .matches(10)
                .totalPlayers(5)
                .totalDuration(Duration.ZERO)
                .avgMatchDuration(Duration.ZERO)
                .gameResults(List.of())
                .build();

        tournamentEventSubscriber.handleTournamentEvent(tournamentEvent);

        verify(notificationService).broadcastTournamentEvent(tournamentEvent);
    }
}
