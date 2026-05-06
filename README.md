# Blog API

Simple Spring Boot blog API with Keycloak JWT auth.

## Requirements
- Java 21
- Maven (or use `./mvnw`)
- PostgreSQL (or use the provided `docker-compose.yml`)
- Keycloak (for JWT tokens)

## Run (local)

```powershell
./mvnw spring-boot:run
```

## Run (docker-compose)

```powershell
docker-compose up -d
./mvnw spring-boot:run
```

## Keycloak setup
- Create a realm named `blog`.
- Create a client for this API and obtain tokens.
- Assign the `admin` realm role to users who can create/update/delete.

The app expects an `Authorization: Bearer <token>` header for protected endpoints.

## API overview

Public (no auth):
- `GET /api/v1/posts`
- `GET /api/v1/posts/{id}`
- `GET /api/v1/categories`
- `GET /api/v1/categories/{id}`
- `GET /api/v1/tags`
- `GET /api/v1/tags/{id}`

Admin only (requires `admin` realm role):
- `POST /api/v1/posts`
- `PUT /api/v1/posts/{id}`
- `DELETE /api/v1/posts/{id}`
- `POST /api/v1/categories`
- `PUT /api/v1/categories/{id}`
- `DELETE /api/v1/categories/{id}`
- `POST /api/v1/tags`
- `PUT /api/v1/tags/{id}`
- `DELETE /api/v1/tags/{id}`

## Example requests

Create a category (admin only):

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/api/v1/categories `
  -Headers @{ Authorization = "Bearer <token>" } `
  -ContentType "application/json" `
  -Body '{"name":"Java"}'
```

Create a post (admin only):

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/api/v1/posts `
  -Headers @{ Authorization = "Bearer <token>" } `
  -ContentType "application/json" `
  -Body '{"title":"Intro","content":"Long enough content...","categoryId":"<uuid>","readingTime":4,"tagIds":["<uuid>"]}'
```
