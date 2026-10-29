# 🚀 Full-Stack Enterprise Monorepo (`monorepo-proyectos`)

Unified **Monorepo** managed with **Turborepo**, **NPM Workspaces**, and **Maven Multi-Module**, hosting two complete Full-Stack Spring Boot + Vanilla JS applications and automated **Playwright E2E** test suites, integrated with **Jira Cloud** and deployed on **AWS (API Gateway + Application Load Balancer + EC2)**.

---

## 🔗 Jira Kanban Board & Task Traceability (`KAN`)

All development tasks, AWS infrastructure upgrades, and E2E automation workflows are tracked in our **Jira Kanban Board** (`Mi espacio de Kanban`):

👉 **[Open Jira Kanban Board (`charroney497.atlassian.net`)](https://charroney497.atlassian.net/jira/software/projects/KAN/boards/1)**

| Jira Issue Key | Task Summary (English) | Status | Direct Link |
| :--- | :--- | :--- | :--- |
| **[KAN-6](https://charroney497.atlassian.net/browse/KAN-6)** | Configure Enterprise Monorepo with Turborepo, NPM Workspaces, and Maven Multi-Module | `Done (Listo)` | [View in Jira](https://charroney497.atlassian.net/browse/KAN-6) |
| **[KAN-7](https://charroney497.atlassian.net/browse/KAN-7)** | Implement Spring Boot BFF API Composition and 4000ms Debounce Vanilla JS Frontend | `Done (Listo)` | [View in Jira](https://charroney497.atlassian.net/browse/KAN-7) |
| **[KAN-8](https://charroney497.atlassian.net/browse/KAN-8)** | Implement End-to-End Automated Test Suite with Playwright across the Monorepo | `Done (Listo)` | [View in Jira](https://charroney497.atlassian.net/browse/KAN-8) |
| **[KAN-9](https://charroney497.atlassian.net/browse/KAN-9)** | Configure AWS Application Load Balancer (ALB) Target Group and Spring Boot Health Endpoint | `Done (Listo)` | [View in Jira](https://charroney497.atlassian.net/browse/KAN-9) |
| **[KAN-10](https://charroney497.atlassian.net/browse/KAN-10)** | Expose Monorepo Services via AWS API Gateway and Restrict Direct Port 8080 Access | `Done (Listo)` | [View in Jira](https://charroney497.atlassian.net/browse/KAN-10) |

---

## 📂 Monorepo Structure

```text
monorepo-proyectos/
├── apps/
│   ├── rickandmorty-bff/       # App 1: Rick & Morty BFF (API Composition + 4000ms Debounce + Playwright)
│   └── taller-backend/         # App 2: Student CRUD REST API (Spring Data JPA + H2 + Vanilla JS + Playwright)
├── turbo.json                  # Turborepo task pipeline and build caching configuration
├── package.json                # Root NPM Workspaces ("workspaces": ["apps/*"])
├── pom.xml                     # Root Maven Multi-Module aggregator (<modules>)
├── docker-compose.yml          # Multi-container orchestration for AWS EC2 (Ports 8080 & 8081)
├── deploy.sh                   # One-command deployment update script for AWS EC2
└── README.md                   # English documentation and AWS/Jira architecture guide
```

---

## ☁️ AWS Cloud Architecture (API Gateway + Load Balancer + EC2)

To prevent exposing port `8080` / `8081` directly to the public internet, the deployment uses a 3-tier cloud entry architecture on AWS:

- **Live API Gateway Endpoint (Rick & Morty BFF)**: [https://fm6xfflx02.execute-api.us-east-1.amazonaws.com/](https://fm6xfflx02.execute-api.us-east-1.amazonaws.com/)
- **Live API Gateway Endpoint (Student CRUD API)**: [https://z0yreha7d8.execute-api.us-east-1.amazonaws.com/](https://z0yreha7d8.execute-api.us-east-1.amazonaws.com/)
- **Application Load Balancer DNS**: `http://alb-monorepo-1323516528.us-east-1.elb.amazonaws.com`

```text
[ Client Browser ]
        │
        ▼  HTTPS (Port 443 - Public API Gateway URL)
[ AWS API Gateway (HTTP API Proxy) ]
        │
        ▼  HTTP (Port 80)
[ AWS Application Load Balancer (ALB) + Target Groups ]
        │  Health Check: GET /api/personaje/health (HTTP 200 OK)
        ▼  Private Security Group Rule (Port 8080 / 8081 restricted to ALB only)
[ AWS EC2 Instance (Docker Containers: rickandmorty-bff & taller-backend) ]
```

---

## 🛠️ Monorepo Commands (Turborepo & Maven)

### 1. Build Both Applications (Maven Multi-Module / Turborepo)
```powershell
# Using Maven Multi-Module from the root
.\mvnw.cmd clean package -DskipTests

# Or using Turborepo
npx turbo run build
```

### 2. Run End-to-End Tests (Playwright via Turborepo)
```powershell
# Run all E2E tests across the monorepo
npm test

# Run E2E tests for Rick and Morty BFF only
npm run test:rickandmorty

# Run E2E tests for Student CRUD (taller-backend) only
npm run test:taller
```

### 3. Deploy / Update on AWS EC2 with Docker
```bash
chmod +x deploy.sh
./deploy.sh
```
