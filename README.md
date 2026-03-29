# OrbitBase — Space Mission Tracker

OOAD Mini Project | UE23CS352B | PES University | Batch 2023–27

Real-world space mission data via Launch Library 2 API, built with Spring Boot MVC + Thymeleaf + MySQL.

## Tech Stack
- Java 21, Spring Boot 4
- Spring MVC, Spring Security, Spring Data JPA
- Thymeleaf, MySQL 8, Hibernate 6
- Launch Library 2 API (thespacedevs.com)

## Setup

### Prerequisites
- Java 21
- MySQL 8

### Steps

1. Clone the repo
   git clone https://github.com/YOUR_USERNAME/orbitbase.git
   cd orbitbase

2. Create the database
   mysql -u root -p -e "CREATE DATABASE orbitbase;"

3. Copy the example properties and fill in your credentials
   cp application.properties.example src/main/resources/application.properties

4. Run
   ./mvnw spring-boot:run

5. Open http://localhost:8080

## Team

| Member | Use Case | Pattern | Principle |
|--------|----------|---------|-----------|
| M1 | Mission Management | Factory | SRP |
| M2 | Launch Management | Observer | OCP |
| M3 | Crew Management | Builder | DIP |
| M4 | Agency & Rocket | Facade | ISP |