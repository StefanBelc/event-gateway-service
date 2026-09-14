Event Gateway Service

A straightforward gateway service designed to stream real-time tournament statistics from Apache Kafka directly to frontend dashboard clients.
Built using standard Java WebSockets and a traditional synchronous model, this service acts as a direct relay layer for event distribution.

Architectural Blueprint
[Tournament Services] -> (Kafka Topic) -> [Event Gateway Service] -> (Standard WebSockets) -> [Dashboard UI]

