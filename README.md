# Policy Hub

My bachelor thesis in the context of dataspaces. This module lets the user to run a Spring Boot application with JPA persistence, built with Java 21.
The Policy Hub is a web application allowing developers in a dataspace to validate, upload, and download policies and download JAR ready policy evaluation EDC extensions for embedding them into the EDC Connector.

## Prerequisites

- Java Development Kit (JDK) 21
- Docker and Docker Compose
- Gradle (or use the included Gradle wrapper)

## Getting Started

Follow these steps to start the application:

1. **Start Docker services**:
   <br />Run the following command to start the necessary services for the application:
   ```bash
   docker-compose up -d
   ```

2. **Run the application**:
   <br />Use the Gradle wrapper to start the Spring Boot application:
   ```bash
   ./gradlew bootRun
   ```

3. The application should now be running and accessible based on its configured settings.

## Project Structure

```
policy-hub/
├── src/
│   └── main/
│       ├── java/               # Core application code
│       └── resources/          # Application resources
├── validation/                 # Policy validation logic
├── policy_evaluation/          # Policy evaluation components
│   └── edc_extension_storage/  # EDC extensions
└── test-utils/                 # Testing utilities
```
