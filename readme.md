# Rate Limiter Spring Boot Application

This project implements a customizable rate limiter using Spring Boot. It demonstrates how to use interceptors, custom annotations, and service layers to enforce API rate limiting.

## Features

- Configurable rate limiting (requests per second, burst capacity)
- Token bucket algorithm implementation
- Custom annotation for rate-limited endpoints
- Global exception handling for rate limit violations
- Easily extendable and configurable
- **Authentication**: OAuth2, JWT for secure client identification

## Implementation Overview

This project is implemented as a Spring Boot application that uses a HandlerInterceptor to intercept incoming API requests and apply rate limiting.

### Core Components

1. **RateLimiterAlgorithm (Interface)**: Located in `com.example.ratelimiter.service`, this interface defines the contract for any rate-limiting algorithm. This allows for pluggable strategies.

2. **TokenBucketRateLimiter (Implementation)**: A default, in-memory implementation of the Token Bucket algorithm. For distributed environments, the state in this class should be moved to a shared cache like Redis.

3. **@RateLimit (Annotation)**: A custom annotation that can be placed on any controller method to specify its rate limit (e.g., `@RateLimit(permits = 5, period = 1, unit = TimeUnit.MINUTES)`).

4. **RateLimitingInterceptor**: This Spring HandlerInterceptor checks for the `@RateLimit` annotation on handler methods. If present, it uses the configured `RateLimiterAlgorithm` to check if the request should be allowed or denied. It identifies clients by their IP address.

5. **GlobalExceptionHandler**: A `@ControllerAdvice` that catches `RateLimitExceededException` and returns a standard HTTP 429 Too Many Requests response to the client.

## Project Structure

```
.
├── pom.xml
├── src
│   └── main
│       ├── java
│       │   └── com
│       │       └── example
│       │           └── ratelimiter
│       │               ├── RateLimiterApplication.java
│       │               ├── config
│       │               │   ├── RateLimiterConfig.java
│       │               │   └── WebMvcConfig.java
│       │               ├── controller
│       │               │   └── ApiController.java
│       │               ├── exception
│       │               │   ├── GlobalExceptionHandler.java
│       │               │   └── RateLimitExceededException.java
│       │               ├── interceptor
│       │               │   ├── RateLimit.java
│       │               │   └── RateLimitingInterceptor.java
│       │               └── service
│       │                   ├── RateLimiterAlgorithm.java
│       │                   └── impl
│       │                       └── TokenBucketRateLimiter.java
│       └── resources
│           └── application.properties
└── readme.md
```

## Configuration

Edit `src/main/resources/application.properties` to set rate limiting and other properties:

```properties
server.port=8080
spring.application.name=rate-limiter
logging.level.org.springframework=INFO
logging.level.com.example.ratelimiter=DEBUG

# Rate Limiting Configuration
rate.limiter.enabled=true
rate.limiter.requests.per.second=5
rate.limiter.burst.capacity=10

# Database Configuration (if applicable)
# spring.datasource.url=jdbc:mysql://localhost:3306/rate_limiter_db
# spring.datasource.username=root
# spring.datasource.password=your_password
# spring.jpa.hibernate.ddl-auto=update

# Other application-specific properties can be added here.
```

## How to Use

1. **Run the Application**: Start the `RateLimiterApplication`.
2. **Test the Endpoints**:
   - `GET /api/resource1`: This endpoint is limited to 2 requests per 10 seconds. Subsequent requests within the window will receive a 429 error.
   - `GET /api/resource2`: This endpoint has no rate limit.

## How to Add a Custom Rate Limiting Algorithm

1. Create a new class that implements the `RateLimiterAlgorithm` interface.
2. Implement the `isAllowed(...)` method with your custom logic.
3. In `RateLimiterConfig.java`, change the `rateLimiterAlgorithm()` bean to return an instance of your new class. Spring's dependency injection will handle the rest.

## Extending

- To change the rate limiting algorithm, implement the `RateLimiterAlgorithm` interface.
- To add new endpoints, create new controllers and annotate methods with `@RateLimit`.

## License

MIT License
