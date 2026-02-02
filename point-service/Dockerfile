# Build stage
FROM maven:3.9.4-eclipse-temurin-21 AS builder

WORKDIR /build

# Copy pom.xml
COPY pom.xml .

# Copy source code
COPY src ./src

# Build application
RUN mvn clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Copy built jar from builder
COPY --from=builder /build/target/*.jar app.jar

# Expose port
EXPOSE 8091

# Run application
ENTRYPOINT ["java", "-jar", "app.jar"]
