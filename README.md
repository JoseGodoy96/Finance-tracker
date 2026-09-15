# FinTrack

Personal finance tracker where you register your income and expenses, and an AI-powered categorizer (Claude Haiku) suggests the right category as you type the description. Built as a learning project with production-grade patterns: containerized services, JWT-based authentication, layered backend architecture, and a React SPA with reactive AI suggestions.

## Stack

**Backend:**
- Java 21 · Spring Boot 4.1 · Spring Security · Spring Data JPA
- PostgreSQL 16
- JWT (jjwt library)
- Anthropic Claude API (`claude-haiku-4-5`)

**Frontend:**
- React 19 · Vite 8 · React Router 6
- CSS Modules
- Custom `fetch` wrapper for JWT handling

**Infrastructure:**
- Docker + Docker Compose (multi-stage builds)
- Nginx (frontend serving)

## Features

### AI-powered category suggestions ⭐

As you type a transaction description, FinTrack asks Claude Haiku which category best fits (from your list of system + personal categories). The suggestion appears in the dropdown within a second, and you can accept it or override.

If the AI service is unavailable, the app falls back to a keyword-based rule set, so classification never breaks.

### Everything else

- User registration and login with hashed passwords (BCrypt).
- JWT-based stateless authentication with a Spring Security filter.
- CRUD for personal categories (with default system categories seeded on first run).
- CRUD for transactions, including in-place editing.
- Two-tier category model: shared system categories + per-user personal ones.
- One-command deployment via Docker Compose.

## Getting started

### Prerequisites

- Docker and Docker Compose (v2+).
- An Anthropic API key with credit ([get one here](https://console.anthropic.com/)).

### Setup

1. Clone the repository:

```bash
   git clone https://github.com/JoseGodoy96/Finance-tracker.git
   cd Finance-tracker
```

2. Create a `.env` file in the project root with the following variables:

```env
   POSTGRES_DB=financeapp
   POSTGRES_USER=postgres
   POSTGRES_PASSWORD=postgres

   SPRING_DATASOURCE_URL=jdbc:postgresql://db:5432/financeapp
   SPRING_DATASOURCE_USERNAME=postgres
   SPRING_DATASOURCE_PASSWORD=postgres

   JWT_SECRET=change-this-to-a-long-random-string-of-at-least-32-characters
   ANTHROPIC_API_KEY=sk-ant-your-key-here
```

3. Build and start all services:

```bash
   docker compose up -d --build
```

   First build takes a few minutes (Postgres, Node, Maven images + dependencies). Subsequent builds are much faster thanks to Docker layer caching.

4. Once running, open [http://localhost:3000](http://localhost:3000) and register a user.

### Useful commands

```bash
docker compose ps                # Check running services
docker compose logs -f backend   # Tail backend logs
docker compose down              # Stop everything (data preserved in volume)
docker compose down -v           # Stop and wipe DB volume
```

## Environment variables

All secrets and connection settings live in the `.env` file at the project root (git-ignored). The Docker Compose file reads them and forwards them to each container.

| Variable | Description | Example |
|---|---|---|
| `POSTGRES_DB` | Name of the database created on first run | `financeapp` |
| `POSTGRES_USER` | Database superuser | `postgres` |
| `POSTGRES_PASSWORD` | Password for the database user | `postgres` |
| `SPRING_DATASOURCE_URL` | JDBC URL. Uses the service name `db` as host | `jdbc:postgresql://db:5432/financeapp` |
| `SPRING_DATASOURCE_USERNAME` | Same as `POSTGRES_USER` | `postgres` |
| `SPRING_DATASOURCE_PASSWORD` | Same as `POSTGRES_PASSWORD` | `postgres` |
| `JWT_SECRET` | HMAC key used to sign JWTs. Must be at least 32 chars | `some-long-random-string-32-chars-min` |
| `ANTHROPIC_API_KEY` | Your Anthropic API key with credit | `sk-ant-...` |

## How the AI categorization works

The AI feature is a small, resilient integration with three parts:

### 1. The prompt

When the user types a description in the transactions form, the frontend debounces the input (800 ms) and calls `POST /api/ai/suggest-category` with the description. The backend builds a prompt like:

> You are a financial-transaction classifier. Available categories: Salary, Freelance, Investments, Other income, Food, Transport, Housing, Entertainment, Health, Other expense. Classify this transaction: '<description>'. Respond ONLY with the exact name of one category, no explanations or quotes.

### 2. The call

The backend uses Spring's `RestTemplate` to call the Anthropic Messages API (`claude-haiku-4-5`) with a small `max_tokens` budget. The response is parsed to extract the raw text, trimmed, and matched (case-insensitive) against the system categories.

### 3. The fallback

If anything goes wrong (network error, invalid API key, insufficient credit, unexpected response shape), the service catches the exception and switches to a keyword-based rule set. That way the app always returns a category, and classification never breaks for the user.

The whole flow is coordinated inside `CategorySuggestionService`, which keeps the fallback logic isolated from the HTTP client (`AnthropicClient`).

## API overview

All endpoints under `/api/**` require a JWT in the `Authorization: Bearer <token>` header, except `/api/auth/**`.

### Auth
- `POST /api/auth/register` — create a user.
- `POST /api/auth/login` — obtain a JWT.

### Categories
- `GET /api/categories` — list system + personal categories for the current user.
- `POST /api/categories` — create a personal category.
- `DELETE /api/categories/{id}` — delete one of your personal categories.

### Transactions
- `GET /api/transactions` — list your transactions.
- `POST /api/transactions?categoryId={id}` — create a transaction.
- `PUT /api/transactions/{id}?categoryId={id}` — update a transaction.
- `DELETE /api/transactions/{id}` — delete a transaction.

### AI
- `POST /api/ai/suggest-category` — takes a `{ "description": "..." }` and returns the suggested `CategoryResponse`.

## Project structure

```
Finance-tracker/
├── backend/                    # Spring Boot app
│   ├── src/main/java/com/chema/db/backend/
│   │   ├── config/             # Security, CORS, JWT filter, RestTemplate bean
│   │   ├── controller/         # REST controllers
│   │   ├── dto/                # Request/response DTOs and mappers
│   │   ├── exception/          # Custom exceptions + GlobalExceptionHandler
│   │   ├── model/              # JPA entities
│   │   ├── repository/         # Spring Data repositories
│   │   └── service/            # Business logic (incl. AnthropicClient)
│   ├── Dockerfile              # Multi-stage: Maven build → JRE runtime
│   └── .dockerignore
├── frontend/                   # React + Vite app
│   ├── src/
│   │   ├── api/                # HTTP wrapper + API modules per domain
│   │   ├── components/         # Reusable UI (Navbar, PrivateRoute)
│   │   ├── pages/              # One component per route
│   │   └── styles/             # Shared CSS Modules
│   ├── Dockerfile              # Multi-stage: Node build → Nginx runtime
│   ├── nginx.conf              # SPA-friendly config with try_files fallback
│   └── .dockerignore
├── docker-compose.yml          # db + backend + frontend services
├── .env                        # Local secrets (git-ignored)
└── README.md
```

## Roadmap

- [ ] Dashboard with monthly summaries and charts.
- [ ] Refresh tokens for longer sessions.
- [ ] Deploy to a public cloud (Railway or Render).
- [ ] Add integration tests for controllers.
- [ ] Multi-currency support.

## License

MIT