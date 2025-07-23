package com.example.ratelimiter.interceptor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * Annotation to apply rate limiting to a controller method.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RateLimit {
    /** The maximum number of permits allowed within the specified time period. */
    int permits();

    /** The time period for the rate limit. */
    int period();

    /** The unit of time for the period. */
    TimeUnit unit() default TimeUnit.SECONDS;
}
