Event Gateway Service

A high-throughput, cloud-native API Gateway designed to stream real-time tournament statistics from Apache Kafka directly to frontend dashboard clients.
Built using a fully reactive, non-blocking paradigm, this service acts as an event distribution layer capable of handling massive client concurrency with minimal resource overhead.


 Architectural Blueprint
[Tournament Services] -> (Kafka Topic) -> [Event Gateway Service] -> (WebSockets) -> [Dashboard UI]
                                             └─ Reactor Kafka      └─ Spring WebFlux

                                             
Edge Routing Layer: Built on Spring Cloud Gateway running on an embedded, asynchronous Netty server (fully bypassing blocking Tomcat threads).
Reactive Pipeline: Connects incoming browser WebSocket sessions directly to an active Apache Kafka event stream via Reactor Kafka.

Zero-Copy Efficiency: Utilizes StringDeserializer on the Kafka consumer layer. By treating tournament payloads as plain-text JSON string streams, the gateway avoids costly Java object instantiation and CPU-heavy re-serialization cycles, pumping raw bytes directly to the UI.
Resource Footprint: Leverages a lightweight, non-blocking event-loop model, ensuring thousands of open, long-lived WebSocket dashboard connections consume only a handful of system threads.




 Tech Stack
Java 21 (LTS) (Utilizing modern virtual thread compatibility and runtime optimizations)
Spring Boot 4.0.x (Cloud-native framework baseline)
Spring Cloud Gateway & WebFlux (Reactive edge routing & WebSockets)
Reactor Kafka (Reactive, backpressure-aware event streaming)
