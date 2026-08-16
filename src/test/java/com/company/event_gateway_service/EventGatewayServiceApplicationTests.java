package com.company.event_gateway_service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@EmbeddedKafka
@TestPropertySource(properties = {
		"spring.kafka.bootstrap-servers=${spring.embedded.kafka.brokers}",
		"MYAPP_KAFKA_BOOTSTRAP_SERVERS=${spring.embedded.kafka.brokers}"
})
class EventGatewayServiceApplicationTests {

	@Test
	void contextLoads() {
	}

}
