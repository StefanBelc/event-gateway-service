FROM maven:3.9.4-eclipse-temurin-20-alpine AS builder
WORKDIR /build


COPY pom.xml .
COPY src ./src


RUN mvn clean package -DskipTests

FROM eclipse-temurin:20-jre-alpine
WORKDIR /app

COPY --from=builder /build/target/event-gateway-service-0.0.1-SNAPSHOT.jar app.jar


EXPOSE 8090


ENTRYPOINT ["java", "-jar", "app.jar"]
