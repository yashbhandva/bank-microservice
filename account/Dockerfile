# Stage 1: Build the jar
FROM eclipse-temurin:17-jdk-alpine AS builder
WORKDIR /app

# Copy Maven files and download dependencies
COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn
RUN ./mvnw dependency:go-offline -B

# Copy source code and build jar
COPY src ./src
RUN ./mvnw clean package

# Stage 2: Create lightweight runtime image
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

# Copy jar from builder stage
COPY --from=builder /app/target/account-0.0.1-SNAPSHOT.jar app.jar

# Expose port
EXPOSE 8080

# Run the jar
CMD ["java", "-jar", "app.jar"]
