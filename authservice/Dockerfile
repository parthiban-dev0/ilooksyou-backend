FROM maven:3.8.4-openjdk-17-slim AS builder
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn package
FROM openjdk:17-slim AS runtime
WORKDIR /app
COPY --from=builder /app/target/auth-service-1.0-SNAPSHOT.jar ./auth-service.jar
COPY Dockerfile .
EXPOSE 8080
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=prod","auth-service.jar"]
