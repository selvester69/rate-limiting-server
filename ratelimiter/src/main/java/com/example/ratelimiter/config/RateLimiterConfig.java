package com.example.ratelimiter.config;

import com.example.ratelimiter.service.RateLimiterAlgorithm;
import com.example.ratelimiter.service.impl.TokenBucketRateLimiter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RateLimiterConfig {

    /**
     * Provides the rate limiter algorithm bean.
     * To use a different algorithm, simply replace the returned instance here.
     * For example: return new SlidingWindowLogRateLimiter();
     */
    @Bean
    public RateLimiterAlgorithm rateLimiterAlgorithm() {
        return new TokenBucketRateLimiter();
    }
}
