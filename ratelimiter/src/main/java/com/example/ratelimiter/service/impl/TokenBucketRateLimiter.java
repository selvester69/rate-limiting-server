package com.example.ratelimiter.service.impl;

import com.example.ratelimiter.service.RateLimiterAlgorithm;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class TokenBucketRateLimiter implements RateLimiterAlgorithm {
    private final Map<String, Bucket> buckets = new ConcurrentHashMap<>();

    @Override
    public boolean isAllowed(String key, int permits, int period, TimeUnit unit) {
        Bucket bucket = buckets.computeIfAbsent(key, k -> new Bucket(permits, period, unit));
        return bucket.tryAcquire();
    }

    @Override
    public void reset(String key) {
        buckets.remove(key);
    }

    // Inner class for per-key bucket
    private static class Bucket {
        private final int capacity;
        private final int refillRate;
        private final long refillIntervalMillis;
        private AtomicInteger tokens;
        private long lastRefillTimestamp;

        public Bucket(int capacity, int period, TimeUnit unit) {
            this.capacity = capacity;
            this.refillRate = capacity;
            this.refillIntervalMillis = unit.toMillis(period);
            this.tokens = new AtomicInteger(capacity);
            this.lastRefillTimestamp = System.currentTimeMillis();
        }

        public synchronized boolean tryAcquire() {
            refillTokens();
            if (tokens.get() > 0) {
                tokens.decrementAndGet();
                return true;
            }
            return false;
        }

        private void refillTokens() {
            long now = System.currentTimeMillis();
            long timeSinceLastRefill = now - lastRefillTimestamp;

            if (timeSinceLastRefill >= refillIntervalMillis) {
                int tokensToAdd = (int) (timeSinceLastRefill / refillIntervalMillis) * refillRate;
                int newTokenCount = Math.min(capacity, tokens.get() + tokensToAdd);
                tokens.set(newTokenCount);
                lastRefillTimestamp = now;
            }
        }
    }

    @Override
    public boolean allowRequest(String key) {
        // Default implementation using 1 permit, 1 second window
        return isAllowed(key, 1, 1, TimeUnit.SECONDS);
    }

    @Override
    public void onRequest(String key) {
        // No-op for token bucket, as tryAcquire already handles token consumption
    }
}