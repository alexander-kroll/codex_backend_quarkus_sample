# Heroes vs Villains Fight System

A Quarkus-based REST API for managing an epic battle system between heroes and villains. This application demonstrates modern Java enterprise features including REST APIs, database persistence, creative AI-powered descriptions, image uploads, and comprehensive observability.

## Features

### Core Functionality
- **Hero Management**: Full CRUD operations for heroes with attributes like strength, agility, intelligence, and special powers
- **Villain Management**: Complete villain management system with evil plans and dark abilities
- **Fight System**: Automated battle logic that pits heroes against villains with power calculations and outcome determination
- **AI Description Generator**: Creative description generation for heroes and villains (mock implementation, ready for LLM integration)
- **Image Upload Service**: File upload capability for character avatars with validation and storage

### Technical Features
- **RESTful API**: JSON-based REST endpoints for all operations
- **Database Persistence**: PostgreSQL for production with H2 for testing
- **Validation**: Bean validation on all entities
- **OpenAPI/Swagger**: Auto-generated API documentation
- **Health Checks**: Built-in health monitoring endpoints
- **Logging**: Structured logging with configurable levels
- **Tracing**: OpenTelemetry integration for distributed tracing
- **Authentication**: OIDC support (configurable)
- **CORS**: Cross-origin resource sharing enabled for frontend integration
- **Testing**: Comprehensive unit and integration tests

## Tech Stack

- **Quarkus 3.6.0**: Modern Java framework optimized for cloud and containers
- **Java 17**: Latest LTS version of Java
- **Hibernate ORM with Panache**: Simplified JPA for database access
- **PostgreSQL**: Production database
- **H2**: In-memory database for testing
- **RESTEasy Reactive**: Non-blocking REST endpoints
- **SmallRye OpenAPI**: API documentation generation
- **Maven**: Build and dependency management

## Getting Started

### Prerequisites

- Java 17 or later
- Maven 3.9+
- PostgreSQL 12+ (for production mode)
- Docker (optional, for running PostgreSQL)

### Running PostgreSQL with Docker

```bash
docker run --name heroes-postgres -e POSTGRES_PASSWORD=heroes -e POSTGRES_USER=heroes -e POSTGRES_DB=heroesdb -p 5432:5432 -d postgres:15
```

### Build the Application

```bash
mvn clean package
```

### Run in Development Mode

```bash
mvn quarkus:dev
```

The application will start in development mode with hot reload enabled at http://localhost:8080

### Run Tests

```bash
mvn test
```

Tests use an in-memory H2 database and don't require PostgreSQL.

## API Documentation

Once the application is running, you can access:

- **Swagger UI**: http://localhost:8080/swagger-ui
- **OpenAPI Spec**: http://localhost:8080/openapi
- **Health Check**: http://localhost:8080/health

## API Endpoints

### Heroes (`/api/heroes`)

- `GET /api/heroes` - Get all heroes
- `GET /api/heroes/{id}` - Get a specific hero
- `GET /api/heroes/random` - Get a random hero
- `POST /api/heroes` - Create a new hero
- `PUT /api/heroes/{id}` - Update a hero
- `DELETE /api/heroes/{id}` - Delete a hero

### Villains (`/api/villains`)

- `GET /api/villains` - Get all villains
- `GET /api/villains/{id}` - Get a specific villain
- `GET /api/villains/random` - Get a random villain
- `POST /api/villains` - Create a new villain
- `PUT /api/villains/{id}` - Update a villain
- `DELETE /api/villains/{id}` - Delete a villain

### Fights (`/api/fights`)

- `GET /api/fights` - Get all fight history
- `GET /api/fights/{id}` - Get a specific fight
- `POST /api/fights` - Start a new fight (requires heroId and villainId)

### AI Generator (`/api/ai`)

- `POST /api/ai/hero/description` - Generate a creative hero description
- `POST /api/ai/villain/description` - Generate a creative villain description
- `GET /api/ai/hero/special-power` - Generate a random special power
- `GET /api/ai/villain/evil-plan` - Generate a random evil plan

### Image Upload (`/api/upload`)

- `POST /api/upload/image` - Upload an image file (multipart/form-data)

## Example Usage

### Create a Hero

```bash
curl -X POST http://localhost:8080/api/heroes \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Solar Flame",
    "description": "A hero with the power of the sun",
    "level": 10,
    "strength": 85,
    "agility": 75,
    "intelligence": 80,
    "specialPower": "Solar Burst"
  }'
```

### Start a Fight

```bash
curl -X POST http://localhost:8080/api/fights \
  -H "Content-Type: application/json" \
  -d '{
    "heroId": 1,
    "villainId": 1
  }'
```

### Generate a Description

```bash
curl -X POST http://localhost:8080/api/ai/hero/description \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Thunder Strike"
  }'
```

## Configuration

Key configuration properties in `src/main/resources/application.properties`:

```properties
# Database
quarkus.datasource.jdbc.url=jdbc:postgresql://localhost:5432/heroesdb
quarkus.datasource.username=heroes
quarkus.datasource.password=heroes

# HTTP
quarkus.http.port=8080
quarkus.http.cors=true

# OIDC (disabled by default)
quarkus.oidc.enabled=false

# File Uploads
quarkus.http.limits.max-body-size=10M
```

## Project Structure

```
src/
├── main/
│   ├── java/com/codex/
│   │   ├── hero/          # Hero entity and REST resource
│   │   ├── villain/       # Villain entity and REST resource
│   │   ├── fight/         # Fight entity, service, and REST resource
│   │   ├── ai/            # AI description generator service
│   │   └── upload/        # Image upload service
│   └── resources/
│       ├── application.properties
│       └── import.sql     # Initial data
└── test/
    ├── java/com/codex/    # Integration tests
    └── resources/
        └── application.properties  # Test configuration
```

## Development

### Adding New Features

1. Create entity classes in appropriate packages
2. Add REST resources with OpenAPI annotations
3. Implement business logic in service classes
4. Write tests for new functionality
5. Update this README

### Database Migrations

The application uses `drop-and-create` strategy in development. For production, consider:
- Using Flyway or Liquibase for schema migrations
- Changing `quarkus.hibernate-orm.database.generation` to `update` or `validate`

## Deployment

### Building a Native Image

```bash
mvn package -Pnative
```

### Running with Docker

```bash
docker build -f src/main/docker/Dockerfile.jvm -t heroes-villains .
docker run -i --rm -p 8080:8080 heroes-villains
```

## Future Enhancements

- [ ] Real AI integration (OpenAI, Azure OpenAI, etc.)
- [ ] WebSocket support for real-time fight notifications
- [ ] GraphQL API alternative
- [ ] User authentication and authorization
- [ ] Advanced fight strategies and multiplayer battles
- [ ] Leaderboards and statistics
- [ ] Frontend application integration
- [ ] Kubernetes deployment configurations

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes with tests
4. Run `mvn clean verify` to ensure all tests pass
5. Submit a pull request

## License

This project is a sample application for demonstration purposes.
