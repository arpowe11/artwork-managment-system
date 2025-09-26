# Stage 1: build the JAR
FROM maven:3.9.2-eclipse-temurin-17 AS builder
WORKDIR /app
COPY pom.xml .
COPY .mvn .mvn
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: run the app
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY --from=builder /app/target/artwork_backend-0.0.1-SNAPSHOT.jar .
EXPOSE 8443
CMD ["java", "-jar", "artwork_backend-0.0.1-SNAPSHOT.jar"]
