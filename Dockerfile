# Multi-stage build: first build the Spring Boot jar, then run it

FROM eclipse-temurin:17-jdk AS builder
WORKDIR /workspace

# Copy gradle wrapper and build files first for better caching
COPY gradlew gradlew.bat settings.gradle build.gradle /workspace/
COPY gradle /workspace/gradle

# Copy source
COPY src /workspace/src

# Build the jar (skip tests for faster deploy; remove -x test to run tests)
RUN chmod +x gradlew && ./gradlew clean bootJar -x test

FROM eclipse-temurin:17-jdk AS runner
WORKDIR /app

# Copy built jar from builder stage
COPY --from=builder /workspace/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","/app/app.jar"]
