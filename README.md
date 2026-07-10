# Project Warball

![Angular](https://img.shields.io/badge/Angular-DD0031?style=for-the-badge&logo=angular&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white)
![MongoDB](https://img.shields.io/badge/MongoDB-4EA94B?style=for-the-badge&logo=mongodb&logoColor=white)
![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)

## Overview

Project Warball is an automated, browser-based text sports simulation game. Inspired by the [Blaseball](https://www.blaseball.com) style, this project translates a fictional sport played in the fantasy setting of Earisia into a live, interactive web dashboard. The game requires no direct player input; instead, an autonomous backend engine calculates passes, tackles, and shots using dynamic mathematical models, broadcasting the results in real-time to the client.

Design specifications and lore are documented in `warball_documentation.md` (and related reference files such as `Warball.md` / `project_warball.md`).

## System Architecture

This project uses a monorepo structure:

* **Frontend (`frontend/warball/`):** Angular 21 (standalone components), Tailwind CSS, and `HttpClient` for REST. Planned: Server-Sent Events (SSE) for live match feeds.
* **Backend (`backend/`):** Java 21 and Spring Boot 3.x (executable JAR with embedded Tomcat). REST API under `/api/v1/`. Planned: `SimulationEngineService` for autonomous match ticks.

### Database Strategy

* **MySQL (`warball_db`):** Persistent relational storage for teams (and later players/rosters). JPA/Hibernate with `ddl-auto=update`.
* **MongoDB (`warball_live`):** Configured for future live match state and event logs. Not used in Day 1.

## Day 1 Progress (Current State)

Day 1 established a full-stack vertical slice for **teams**: database → Spring Boot API → Angular display.

### Backend (implemented)

| Layer | Details |
|-------|---------|
| **Entity** | `Team` — `id`, `teamName`, `country`, `province` (JPA + Lombok) |
| **Repository** | `TeamRepository` extends `JpaRepository<Team, Long>` |
| **Service** | `TeamService` / `TeamServiceImpl` — create and update logic |
| **Controller** | `TeamRestController` — REST endpoints with CORS for `http://localhost:4200` |

**Teams API** (`/api/v1/teams`):

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/teams` | List all teams |
| `GET` | `/teams/{id}` | Get one team (404 if not found) |
| `POST` | `/teams` | Create team (201 Created) |
| `PUT` | `/teams/{id}` | Update team (200 OK / 404) |

Example response:

```json
{
  "id": 1,
  "teamName": "GALONIAN GLADIATORS",
  "country": "GALONIAN EMPIRE",
  "province": null
}
```

### Frontend (implemented)

| Feature | Details |
|---------|---------|
| **Shell** | `App` — global header + `<router-outlet>` |
| **Routing** | `/` (Home), `/teams` (public team list) |
| **Header** | Shared navigation with `routerLink` |
| **Teams page** | Fetches from API via `TeamService`; displays teams using the `async` pipe |
| **Styling** | Tailwind CSS v4 via PostCSS |

### MySQL (Day 1 schema)

Table `teams`:

* `ID` (INT, auto-increment, PK)
* `TEAM_NAME`
* `COUNTRY`
* `PROVINCE`

New columns can be added by extending the `Team` entity; Hibernate `update` will alter the table without dropping existing data.

## Version 1 Roadmap (Planned)

Features not yet built but planned for v1:

* **Admin UI (Angular):** Reactive Forms to create/edit teams (and later players) via the REST API
* **Random player generator:** Procedural names, races, stats, and traits
* **Player rosters:** 18-player teams stored in MySQL (JSON attributes)
* **Live match feed:** SSE streaming from Spring Boot; match state in MongoDB
* **Team & player directories:** Public views, profiles, and history endpoints
* **DELETE endpoints:** Optional; manual DB cleanup acceptable during development

## Future Development

* User accounts for community voting and input
* Image / pixel art generation for player portraits and team emblems
* Authentication for admin routes before VPS deployment

## Local Development Setup

### Prerequisites

* Node.js & npm (Angular)
* Java 21 & Maven (Spring Boot)
* MySQL Server (check port in `application.properties` — default config uses **3307**)
* MongoDB Server (port 27017) — required later for live simulation

### Recommended tooling

* **VS Code** with Extension Pack for Java, Spring Boot Extension Pack, and Angular language support
* **MySQL Workbench** (or similar) for database management
* **Postman / Thunder Client / curl** for API testing

### Starting the Backend

1. Navigate to `backend/`.
2. Ensure MySQL is running and `warball_db` exists.
3. Update `src/main/resources/application.properties` with your MySQL URL, username, and password.
4. Run the application (embedded Tomcat — no separate server install):

   ```bash
   ./mvnw spring-boot:run
   ```

   Windows:

   ```powershell
   .\mvnw.cmd spring-boot:run
   ```

   Or run `BackendApplication` from VS Code / Spring Boot Dashboard.

5. Verify API: `http://localhost:8080/api/v1/teams`

### Starting the Frontend

1. Navigate to `frontend/warball/`.
2. Install dependencies: `npm install`
3. Start dev server: `ng serve`
4. Open `http://localhost:4200`

### CORS

The backend allows cross-origin requests from `http://localhost:4200` for local development. Use `localhost` (not `127.0.0.1`) in the browser to match the configured origin.

### Packaging note

The backend is packaged as an **executable JAR** (not WAR). Deployment to a VPS is done with `java -jar`, without a standalone Tomcat installation.

## Project Structure (Day 1)

```text
warball/
├── backend/
│   └── src/main/java/com/warball/backend/
│       ├── BackendApplication.java
│       ├── controllers/TeamRestController.java
│       ├── entities/Team.java
│       ├── repositories/TeamRepository.java
│       └── services/TeamService.java, TeamServiceImpl.java
├── frontend/warball/
│   └── src/app/
│       ├── components/   (header, home, team-list)
│       ├── services/team.ts
│       ├── app.routes.ts
│       └── app.config.ts
└── README.md
```

---
*Project Warball is a passion project built to merge web development architecture with deep, narrative-driven tabletop lore.*
