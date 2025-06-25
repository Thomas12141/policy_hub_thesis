# Project Name

A Spring Boot application with JPA persistence, built with Java 21.

## Prerequisites

- Java Development Kit (JDK) 21
- Docker and Docker Compose
- Gradle (or use the included Gradle wrapper)

## Getting Started

Follow these steps to start the application:

1. **Start Docker services**:
   Run the following command to start the necessary services for the application:
   ```bash
   docker-compose up -d
   ```

2. **Run the application**:
   Use the Gradle wrapper to start the Spring Boot application:
   ```bash
   ./gradlew bootRun
   ```

3. The application should now be running and accessible based on its configured settings.