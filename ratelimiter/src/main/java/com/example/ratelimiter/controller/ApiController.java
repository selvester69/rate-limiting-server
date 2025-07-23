package com.example.ratelimiter.controller;

import com.example.ratelimiter.interceptor.RateLimit;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api")
public class ApiController {

    @GetMapping("/resource1")
    @RateLimit(permits = 2, period = 10, unit = TimeUnit.SECONDS)
    public ResponseEntity<String> getLimitedResource() {
        return ResponseEntity.ok("This is a rate-limited resource (2 requests per 10 seconds).");
    }

    @GetMapping("/resource2")
    public ResponseEntity<String> getUnlimitedResource() {
        return ResponseEntity.ok("This resource has no rate limit.");
    }
}
