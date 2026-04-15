# OrbitBase

A Spring Boot web application that tracks space launches, missions, agencies, rockets, and astronauts using real-time data from the [Launch Library 2 API](https://ll.thespacedevs.com/2.3.0).

Built as a PES University UE23CS352B (OOAD) Mini Project demonstrating MVC architecture, GoF design patterns, and SOLID principles.

---

## Table of Contents

- [Tech Stack](#tech-stack)
- [Features](#features)
- [Project Structure](#project-structure)
- [Design Patterns](#design-patterns)
- [SOLID Principles](#solid-principles)
- [Prerequisites](#prerequisites)
- [Setup and Running](#setup-and-running)
- [Pages Reference](#pages-reference)
- [Team](#team)

---

## Tech Stack

| Layer | Technology |
|-------|-----------|
| Language | Java 21 |
| Framework | Spring Boot 4.0.5 |
| Web MVC | Spring Web MVC + Thymeleaf |
| Persistence | Spring Data JPA + Hibernate 7 |
| Database | MySQL 8 |
| HTTP Client | Spring WebFlux (WebClient) |
| Build Tool | Maven (mvnw wrapper included) |
| Utilities | Lombok |
| Data Source | Launch Library 2 API v2.3.0 |

---

## Features

- Live dashboard showing counts of all 5 database tables
- Paginated sync from Launch Library 2 API on startup and every hour
- Astronaut list with **sort by name / nationality** (Strategy pattern)
- Astronaut **search by nationality**
- Full **astronaut detail page** with profile image, bio, agency, DOB
- Pages for all 5 tables: Launches, Missions, Agencies, Rockets, Crew
- Duplicate-safe sync — re-running never creates duplicate rows

---

## Project Structure

```
src/main/java/com/orbitbase/
├── config/
│   └── SecurityConfig.java              # Permits all requests (demo config)
├── controller/
│   ├── CrewController.java              # /crew endpoints
│   └── DataController.java              # /dashboard, /launches, /missions, /agencies, /rockets
├── dto/
│   └── LaunchApiResponse.java           # Jackson DTOs mapping LL2 API JSON responses
├── model/
│   ├── Agency.java
│   ├── Astronaut.java
│   ├── Launch.java
│   ├── Mission.java
│   └── Rocket.java
├── repository/
│   ├── AgencyRepository.java
│   ├── AstronautRepository.java
│   ├── LaunchRepository.java
│   ├── MissionRepository.java
│   └── RocketRepository.java
└── service/
    ├── AstronautBuilder.java            # GoF Builder pattern
    ├── AstronautMapper.java             # SRP — owns DTO → Entity conversion
    ├── AstronautService.java            # Implements IAstronautReader + IAstronautSearch
    ├── DashboardSummary.java            # Data holder returned by Facade
    ├── IAstronautReader.java            # ISP — read/sort/detail operations
    ├── IAstronautSearch.java            # ISP — search operations
    ├── IAstronautService.java           # Original service interface (DIP)
    ├── OrbitDataFacade.java             # Facade pattern — unified dashboard access
    ├── SortByNameStrategy.java          # Strategy: sort alphabetically by name
    ├── SortByNationalityStrategy.java   # Strategy: sort by nationality
    ├── SortStrategy.java                # Strategy interface
    └── SpaceDataSyncService.java        # Scheduled LL2 API sync (launches + astronauts)

src/main/resources/
├── application.properties              # DB config — gitignored, create from example
├── application.properties.example      # Template for teammates
└── templates/
    ├── dashboard.html
    ├── crew.html
    ├── crew-detail.html
    ├── launches.html
    ├── missions.html
    ├── agencies.html
    └── rockets.html
```

---

## Design Patterns

### 1. Builder Pattern — Creational

**Class:** `AstronautBuilder`

The LL2 API returns astronauts with 7 optional fields. A plain 7-argument constructor would be unreadable and fragile. `AstronautBuilder` constructs `Astronaut` objects step-by-step with fluent `withX()` methods. The `build()` method enforces invariants — `apiId` and `name` are required and throw `IllegalStateException` if missing.

```java
Astronaut a = new AstronautBuilder()
    .withApiId(dto.getId())
    .withName(dto.getName())
    .withNationality("American")
    .withBio(dto.getBio())
    .withDateOfBirth(dto.getDateOfBirth())
    .build();
```

### 2. Facade Pattern — Structural

**Class:** `OrbitDataFacade`

The dashboard page needs row counts from all 5 tables. Without a facade, `DataController` would need to inject 5 separate repositories. `OrbitDataFacade` hides this complexity behind a single `getDashboardSummary()` method, returning a `DashboardSummary` with all counts in one call.

```java
// Controller only ever needs one dependency for dashboard data
DashboardSummary summary = orbitDataFacade.getDashboardSummary();
```

### 3. Strategy Pattern — Behavioral

**Interface:** `SortStrategy`
**Implementations:** `SortByNameStrategy`, `SortByNationalityStrategy`

The sort behaviour for the crew list is selected at runtime via the `?sort=` query parameter. `AstronautService.getAllAstronautsSorted(strategy)` accepts any `SortStrategy`. Adding a new sort order requires only a new implementation class — no changes to the controller or service (see OCP below).

```
GET /crew?sort=name        → SortByNameStrategy applied
GET /crew?sort=nationality → SortByNationalityStrategy applied
```

### 4. Front Controller Pattern — Framework-enforced

Spring MVC's `DispatcherServlet` acts as a Front Controller. Every HTTP request enters through one central entry point, which reads `@GetMapping` annotations and dispatches to the correct `@Controller` method. This pattern is enforced automatically by the Spring framework — no extra code required.

---

## SOLID Principles

### DIP — Dependency Inversion Principle

`CrewController` declares fields of type `IAstronautReader` and `IAstronautSearch` (abstractions). Spring injects `AstronautService` (the concrete class) at runtime. The controller never imports or instantiates `AstronautService` directly. Swapping implementations (e.g. for testing) requires zero changes to the controller.

### SRP — Single Responsibility Principle

`AstronautMapper` has exactly one job: convert `AstronautDto` (the LL2 API format) into an `Astronaut` entity (domain model). Previously this mapping logic was embedded in `SpaceDataSyncService`, mixing HTTP/scheduling concerns with data transformation. After extraction, each class has one clear reason to change.

### ISP — Interface Segregation Principle

`IAstronautService` was split into two focused interfaces:
- `IAstronautReader` — `getAllAstronauts()`, `getAllAstronautsSorted()`, `getAstronautById()`
- `IAstronautSearch` — `searchByNationality()`

`CrewController` injects only the interface relevant to each endpoint. No client is forced to depend on methods it does not use.

### OCP — Open/Closed Principle

`AstronautService` and `CrewController` are **closed for modification**. Adding a new sort order (e.g. sort by date of birth) requires only writing a new `SortByDateOfBirthStrategy` class — zero changes to existing code. The `SortStrategy` interface is the extension point.

---

## Prerequisites

- **Java 21** or later
- **MySQL 8** running locally
- **Git Bash** (recommended on Windows — the `mvnw` script requires a Unix shell)
- Internet access (for first-time Maven dependency download and LL2 API sync)

---

## Setup and Running

### 1. Clone the repository

```bash
git clone https://github.com/amogh-4050/orbitbase.git
cd orbitbase
```

### 2. Create the MySQL database

Log into MySQL and run:

```sql
CREATE DATABASE orbitbase;
```

### 3. Configure application.properties

```bash
cp application.properties.example src/main/resources/application.properties
```

Open `src/main/resources/application.properties` and set your MySQL password:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/orbitbase
spring.datasource.username=root
spring.datasource.password=YOUR_PASSWORD_HERE
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect
spring.thymeleaf.cache=false
server.port=8080
```

### 4. Run the application

Open **Git Bash** in the project folder and run:

```bash
./mvnw spring-boot:run
```

> **Windows CMD / PowerShell users:** `mvnw.cmd` may not find PowerShell on your PATH. Use Git Bash with `./mvnw spring-boot:run` instead.

### 5. Open in browser

```
http://localhost:8080/dashboard
```

On first startup Hibernate creates all 5 tables automatically. The background sync jobs fire immediately — watch the terminal for:

```
INFO  : Astronaut sync complete. Processed 25 astronauts.
INFO  : Sync complete. Processed 25 launches.
```

After that, all pages will show live data.

---

## Pages Reference

| URL | What you see |
|-----|-------------|
| `http://localhost:8080/` | Redirects to dashboard |
| `http://localhost:8080/dashboard` | Counts for all 5 tables (via Facade) |
| `http://localhost:8080/launches` | Launch table with status, pad, agency, mission |
| `http://localhost:8080/missions` | Mission table with type and orbit |
| `http://localhost:8080/agencies` | Agency table with abbreviation and type |
| `http://localhost:8080/rockets` | Rocket table with family and variant |
| `http://localhost:8080/crew` | Astronaut table with search and sort |
| `http://localhost:8080/crew?sort=name` | Astronauts sorted A→Z by name |
| `http://localhost:8080/crew?sort=nationality` | Astronauts sorted by nationality |
| `http://localhost:8080/crew/{id}` | Full detail page for one astronaut |
| `http://localhost:8080/crew/search?nationality=American` | Filtered astronaut list |
| `http://localhost:8080/crew/sync` | Trigger manual sync, redirects to /crew |

---

## Team

**PES University — UE23CS352B Object Oriented Analysis & Design**

| Member | Use Case | Pattern | Principle |
|--------|----------|---------|-----------|
| Abhinav W | Astronaut / Crew sync, display, search, sort | Builder, Strategy, Facade | DIP, SRP, ISP, OCP |
| Amogh | Launch / Mission / Agency / Rocket sync | _(pattern)_ | _(principle)_ |
| _(T3)_ | _(feature)_ | _(pattern)_ | _(principle)_ |
| _(T4)_ | _(feature)_ | _(pattern)_ | _(principle)_ |

Data sourced from [The Space Devs — Launch Library 2](https://thespacedevs.com/llapi).
