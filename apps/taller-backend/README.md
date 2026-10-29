# Taller Backend

A student management application with a Spring Boot REST API, a persistent H2 database, and a web interface built with vanilla JavaScript and Tailwind CSS.

## Requirements

- Java 17
- Maven 3.9 or later
- Node.js 20 or later and npm (for end-to-end tests)

## Run the application

From the project root, start the Spring Boot application:

```powershell
mvn spring-boot:run
```

Open the web interface at [http://localhost:8080/](http://localhost:8080/). Spring Boot serves `index.html` and the REST API from the same origin; a separate frontend server is not required.

The API is available at [http://localhost:8080/api/estudiantes](http://localhost:8080/api/estudiantes).

The H2 database is stored in `data/tallerdb.mv.db` and is excluded from Git. Set the `TALLER_DB_PATH` environment variable to use a different absolute database path.

On first startup, `src/main/resources/data.sql` inserts the sample students. The inserts are idempotent, so they do not add duplicates on subsequent startups.

## Run with Docker

Build the image and start the container from the project root:

```powershell
docker build -t taller-backend:latest .
docker run --rm -p 8080:8080 --name taller-backend taller-backend:latest
```

The Docker image uses Java 17. The H2 database file is stored inside the container, so it is removed when the container is deleted. Mount a host directory at `/app/data` if the database must persist across container runs.

## Run the end-to-end tests

With the application running, install the Node.js dependencies and the Playwright Chromium browser:

```powershell
npm install
npx playwright install chromium
npm run test:e2e
```

To run the tests with a visible browser:

```powershell
npm run test:e2e:headed
```

The end-to-end test creates a student with a unique email address, checks the student card and profile dialog, and deletes the test record when it finishes.

## REST API

All student endpoints use the `/api/estudiantes` base path.

| Method | Path | Description |
| --- | --- | --- |
| GET | `/api/estudiantes` | List all students |
| GET | `/api/estudiantes/{id}` | Get a student by ID |
| POST | `/api/estudiantes` | Create a student |
| PUT | `/api/estudiantes/{id}` | Update a student |
| DELETE | `/api/estudiantes/{id}` | Delete a student |

## Project structure

| Path | Purpose |
| --- | --- |
| `src/main/java` | Spring Boot application, REST controller, service, repository, and entity |
| `src/main/resources` | Application configuration and sample database data |
| `src/test/java` | Java integration tests |
| `tests` | Playwright end-to-end tests |
| `postman` | Postman collection and request definitions |
| `index.html` | Web interface served by Spring Boot |
