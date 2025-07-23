package com.example.ratelimiter.service;

import java.util.concurrent.TimeUnit;

public interface RateLimiterAlgorithm {
    boolean allowRequest(String key);

    void onRequest(String key);

    void reset(String key);

    /**
     * Checks if a request identified by a key should be allowed based on the
     * defined rate limit.
     */
    boolean isAllowed(String key, int permits, int period, TimeUnit timeUnit);
}
