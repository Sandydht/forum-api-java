# Forum API Java
A robust, RESTful API built with Java and Spring Boot, designed with **Clean Architecture** and **Domain-Driven Design (DDD)** principles in mind. This project demonstrates advanced error handling, layered architecture, and comprehensive testing.

## Architecture
This project follows the **Clean Architecture** pattern to ensure the business logic remains independent of framework and external UI
- **Domain** contains core business rules.
- **Applications** contains use cases and orchestration logic.
- **Infrastructures** contains technical implementations.
- **Interfaces** expose the system to external clients (HTTP API).

## Technology Stack
- **Java 17/21**
- **Spring Boot 3**
- **Java Persistence API (JPA)**
- **JUnit 5 & Mockito** (Testing)
- **Jackson** (JSON Processing)
- **Maven** (Build Tool)

## Getting Started
### Prerequisites
- JDK 17 or higher.
- Maven 3.8 +.

### Installation
- Clone Repository
    ```shell
    git clone https://github.com/Sandydht/forum-api-java.git
    ```
- Navigate to the project directory
    ```shell
    cd forum-api-java
    ```
- Build the project
    ```shell
    mvn clean install 
    ```
- Run the application
    ```shell
    mvn spring-boot:run
    ```