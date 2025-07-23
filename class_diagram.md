classDiagram
    class RateLimiterApplication {
        +main(String[] args)
    }

    class RateLimiterConfig {
        +rateLimiterAlgorithm() RateLimiterAlgorithm
    }

    class WebMvcConfig {
        -rateLimitingInterceptor RateLimitingInterceptor
        +addInterceptors(InterceptorRegistry registry)
    }

    class RateLimitingInterceptor {
        -rateLimiter RateLimiterAlgorithm
        +preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) boolean
        -getClientKey(HttpServletRequest request) String
    }

    class ApiController {
        +getLimitedResource() ResponseEntity
        +getUnlimitedResource() ResponseEntity
    }

    class GreetingController {
        +sayHello() ResponseEntity
    }

    class RateLimiterAlgorithm {
        <<interface>>
        +isAllowed(String key, int permits, int period, TimeUnit timeUnit) boolean
    }

    class TokenBucketRateLimiter {
        -buckets Map~String, Bucket~
        +isAllowed(String key, int permits, int period, TimeUnit timeUnit) boolean
    }

    class Bucket {
        -capacity int
        -refillRate int
        -tokens AtomicInteger
        +tryAcquire() boolean
    }

    class RateLimit {
        <<annotation>>
        +permits() int
        +period() int
        +unit() TimeUnit
    }

    class RateLimitExceededException {
        +RateLimitExceededException(String message)
    }

    class GlobalExceptionHandler {
        +handleRateLimitExceededException(RateLimitExceededException ex) ResponseEntity
    }

    WebMvcConfig --> RateLimitingInterceptor : has a
    RateLimitingInterceptor --> RateLimiterAlgorithm : has a
    RateLimiterConfig --> RateLimiterAlgorithm : creates
    TokenBucketRateLimiter --|> RateLimiterAlgorithm : implements
    TokenBucketRateLimiter +-- Bucket
    ApiController --> RateLimit : uses
    GreetingController --> RateLimit : uses
    RateLimitingInterceptor ..> RateLimitExceededException : throws
    GlobalExceptionHandler ..> RateLimitExceededException : handles
    WebMvcConfig ..|> WebMvcConfigurer