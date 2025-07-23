package com.example.ratelimiter.interceptor;

import com.example.ratelimiter.exception.RateLimitExceededException;
import com.example.ratelimiter.service.RateLimiterAlgorithm;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class RateLimitingInterceptor implements HandlerInterceptor {

    private final RateLimiterAlgorithm rateLimiter;

    public RateLimitingInterceptor(RateLimiterAlgorithm rateLimiter) {
        this.rateLimiter = rateLimiter;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        RateLimit rateLimitAnnotation = handlerMethod.getMethodAnnotation(RateLimit.class);

        if (rateLimitAnnotation == null) {
            return true; // No rate limit annotation, proceed
        }

        String key = getClientKey(request);
        int permits = rateLimitAnnotation.permits();
        int period = rateLimitAnnotation.period();

        if (!rateLimiter.isAllowed(key, permits, period, rateLimitAnnotation.unit())) {
            throw new RateLimitExceededException("You have exceeded the request limit.");
        }

        return true;
    }

    /**
     * Generates a unique key for the client. Here, we use the IP address.
     * This could be extended to use a user ID, API key, etc.
     */
    private String getClientKey(HttpServletRequest request) {
        return request.getRemoteAddr();
    }
}
