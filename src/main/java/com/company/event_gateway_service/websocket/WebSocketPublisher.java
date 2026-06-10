package com.company.event_gateway_service.websocket;

import com.company.event_gateway_service.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
import tools.jackson.databind.ObjectMapper;

@Component
@RequiredArgsConstructor
public class WebSocketPublisher {

    private static final Logger log = LoggerFactory.getLogger(WebSocketPublisher.class);
    private final NotificationService notificationService;
    private final ObjectMapper objectMapper;


    public Flux<WebSocketMessage> getPublishingFlux(WebSocketSession session) {
        if (session.isOpen()) {
            Flux<Object> mergedEvents = Flux.merge(
                    notificationService.getGameEventStream(),
                    notificationService.getTournamentEventStream(),
                    notificationService.getLeaderboardEventStream()
            );

            return mergedEvents.map(event -> {
                try {
                    String formattedEvent = objectMapper.writeValueAsString(event);
                    log.info("Successfully formatted event: {}", formattedEvent);
                    return session.textMessage(formattedEvent);
                } catch (Exception e) {
                    log.error("Serialization failed {}, {}", session.getId(), e.getMessage());
                    return session.textMessage("{\"error\":\"Serialization failed\"}");
                }
            });
        } else {
            log.error("no websocket session open");
            return Flux.empty();
        }
    }
}

