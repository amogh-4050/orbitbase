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

**1. Clone the repo**
```
git clone https://github.com/YOUR_USERNAME/orbitbase.git
cd orbitbase
```

**2. Install Java 21**
```
sudo nala install openjdk-21-jdk
java -version
```

**3. Install and start MySQL**
```
sudo nala install mysql-server
sudo systemctl start mysql
sudo mysql_secure_installation
```

**4. Create the database**
```
sudo mysql -u root -p
CREATE DATABASE orbitbase;
EXIT;
```

**5. Set up application.properties**
```
cp application.properties.example src/main/resources/application.properties
```
Open `src/main/resources/application.properties` and fill in your MySQL password:
```
spring.datasource.url=jdbc:mysql://localhost:3306/orbitbase
spring.datasource.username=root
spring.datasource.password=YOUR_PASSWORD_HERE

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.thymeleaf.cache=false
server.port=8080
```

**6. Run**
```
./mvnw spring-boot:run
```

**7. Open http://localhost:8080**

> Note: On first run, Hibernate auto-creates all tables and the sync
> service immediately pulls live data from Launch Library 2.
> Check your terminal for `Sync complete` to confirm.

## Project Structure
```
src/main/java/com/orbitbase/
├── config/        # WebClient, Security config
├── controller/    # MVC controllers
├── dto/           # API response mapping classes
├── model/         # JPA entities (DB tables)
├── repository/    # Spring Data JPA interfaces
└── service/       # Business logic + API sync
src/main/resources/
├── templates/     # Thymeleaf HTML pages
└── static/        # CSS, JS, images
```

## Branching Strategy
- `main` — stable, merged code only
- `feature/api-sync` — Teammate 1 (Crew/Astronaut sync)
- `feature/frontend` — Teammate 2 (UI + controllers)

Always pull before starting work:
```
git pull origin main
```

## Team

| Member | Branch | Use Case | Pattern | Principle |
|--------|--------|----------|---------|-----------|
| Amogh | main | Mission + Launch integration | Factory, Observer | SRP |
| T1 | feature/api-sync | Crew + Astronaut sync | Builder | DIP |
| T2 | feature/frontend | UI + Agency + Rocket views | Facade | ISP |

---
