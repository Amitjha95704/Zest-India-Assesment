# Product API

A RESTful Product Management API built using Java and Spring Boot. The application supports product and item management with JWT-based authentication and role-based authorization.

## Tech Stack

* Java 17
* Spring Boot
* Spring Data JPA / Hibernate
* MySQL
* Spring Security
* JWT
* Lombok
* Swagger / OpenAPI
* Docker & Docker Compose

## Features

* Product CRUD operations
* Product items management
* Pagination for products
* Input validation
* Global exception handling
* JWT authentication
* Refresh token rotation
* Role-based authorization
* CORS configuration
* Database indexing
* Docker support
* Swagger API documentation

## Project Structure

```text
src/main/java/com/example/productapi
├── config
├── controller
├── dto
├── entity
├── exception
├── repository
├── security
└── service
```

## API Endpoints

### Authentication

| Method | Endpoint                | Description          |
| ------ | ----------------------- | -------------------- |
| POST   | `/api/v1/auth/register` | Register user        |
| POST   | `/api/v1/auth/login`    | Login                |
| POST   | `/api/v1/auth/refresh`  | Refresh access token |

### Products

| Method | Endpoint                | Description    |
| ------ | ----------------------- | -------------- |
| GET    | `/api/v1/products`      | Get products   |
| GET    | `/api/v1/products/{id}` | Get product    |
| POST   | `/api/v1/products`      | Create product |
| PUT    | `/api/v1/products/{id}` | Update product |
| DELETE | `/api/v1/products/{id}` | Delete product |

### Items

| Method | Endpoint                      | Description       |
| ------ | ----------------------------- | ----------------- |
| POST   | `/api/v1/products/{id}/items` | Add item          |
| GET    | `/api/v1/products/{id}/items` | Get product items |

## Authentication

The API uses JWT authentication.

After login, use the access token in the request header:

```text
Authorization: Bearer <access-token>
```

Roles:

* `USER` - Can view and manage products
* `ADMIN` - Can also delete products

Refresh tokens are stored in the database and rotated when used.

## Pagination

Products support pagination using:

```text
GET /api/v1/products?page=0&size=10
```

## Database

The application uses MySQL.

Create the database:

```sql
CREATE DATABASE product_db;
```

Update the credentials in:

```text
src/main/resources/application.properties
```

Example:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/product_db
spring.datasource.username=root
spring.datasource.password=root

spring.jpa.hibernate.ddl-auto=update
```

## Running Locally

Build the project:

```bash
mvn clean package -DskipTests
```

Run:

```bash
mvn spring-boot:run
```

The API will be available at:

```text
http://localhost:8080
```

## Swagger

Swagger UI:

```text
http://localhost:8080/swagger-ui/index.html
```

## Docker

Build and start the application:

```bash
mvn clean package -DskipTests
docker compose up --build
```

Stop containers:

```bash
docker compose down
```

## Error Handling

The API returns a consistent JSON response for validation errors, missing resources, unauthorized requests, and other application errors.

## Notes

For production deployment, HTTPS should be configured through the application or a reverse proxy/load balancer, and the JWT secret should be provided through environment variables rather than committed to the project.
