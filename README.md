# NEXUS Operations

A RESTful Inventory Management API built with Java 21, Spring Boot and PostgreSQL.

## Tech Stack

- Java 21
- Spring Boot 4.1.2
- Spring Data JPA
- Spring MVC
- Bean Validation
- PostgreSQL 17
- Maven
- JUnit 5
- Mockito
- Docker Compose

## Features

- Material registration and management.
- CRUD REST API.
- Low-stock monitoring.
- Input validation.
- Standardized error responses.
- PostgreSQL persistence.
- Automated tests.

## Getting Started

### Requirements
- Java 21
- Docker
- Docker Compose

### Start the database

From the project root:

    docker compose up -d

### Start the backend

    cd backend
    ./mvnw spring-boot:run

API address: http://localhost:8080

## API Endpoints

Base path: /api/materials

| Method | Endpoint | Description |
|---|---|---|
| GET | /api/materials | List materials |
| GET | /api/materials/{id} | Find material |
| POST | /api/materials | Create material |
| PUT | /api/materials/{id} | Update material |
| DELETE | /api/materials/{id} | Delete material |
| GET | /api/materials/low-stock | List low-stock materials |

## Run Tests

    cd backend
    ./mvnw clean test

## Author

Developed as a backend engineering portfolio project.
