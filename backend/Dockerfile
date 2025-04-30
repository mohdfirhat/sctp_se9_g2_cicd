# Stage 1: Build the Spring Boot app
FROM maven:3.8.5-openjdk-17 AS builder
WORKDIR /app

# Copy the backend code
COPY . .

# Build the project
RUN ./mvnw clean package -DskipTests

# Stage 2: Run the app
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copy the JAR file from the builder stage
COPY --from=builder /app/target/*.jar app.jar

# Expose the app port
EXPOSE 8080

# Pass environment variables to the application
ENTRYPOINT ["java", "-jar", "app.jar"]