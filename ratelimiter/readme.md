# Rate Limiter Project

This project is a Spring Boot application that implements a rate limiting mechanism. It provides a way to control the rate at which requests are processed, ensuring that the application can handle traffic efficiently without being overwhelmed.

## Project Structure

The project is organized as follows:

```
ratelimiter
├── pom.xml                     # Maven configuration file
├── src
│   └── main
│       ├── java
│       │   └── com
│       │       └── example
│       │           └── ratelimiter
│       │               ├── RateLimiterApplication.java  # Main entry point of the application
│       │               ├── config
│       │               │   ├── RateLimiterConfig.java   # Configuration for rate limiting
│       │               │   └── WebMvcConfig.java       # Spring MVC configuration
│       │               ├── controller
│       │               │   └── ApiController.java      # REST controller for API requests
│       │               ├── exception
│       │               │   ├── GlobalExceptionHandler.java  # Global exception handling
│       │               │   └── RateLimitExceededException.java  # Custom exception for rate limit
│       │               ├── interceptor
│       │               │   ├── RateLimit.java           # Annotation for rate limiting
│       │               │   └── RateLimitingInterceptor.java  # Logic for intercepting requests
│       │               └── service
│       │                   ├── RateLimiterAlgorithm.java  # Interface for rate limiting algorithms
│       │                   └── impl
│       │                       └── TokenBucketRateLimiter.java  # Implementation of Token Bucket algorithm
│       └── resources
│           └── application.properties  # Application configuration properties
└── readme.md                   # Project documentation
```

## Setup Instructions

1. **Clone the repository**:
   ```
   git clone <repository-url>
   cd ratelimiter
   ```

2. **Build the project**:
   ```
   mvn clean install
   ```

3. **Run the application**:
   ```
   mvn spring-boot:run
   ```

## Usage

Once the application is running, you can access the API endpoints defined in `ApiController.java`. The rate limiting functionality will be applied based on the configurations set in `RateLimiterConfig.java`.

## Contributing

Contributions are welcome! Please feel free to submit a pull request or open an issue for any enhancements or bug fixes.

## License

This project is licensed under the MIT License. See the LICENSE file for more details.