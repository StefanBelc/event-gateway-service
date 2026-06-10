package com.company.event_gateway_service.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public Object broadcastEvent(Object message) {
        return objectMapper.writeValueAsString(message);
    }
}
