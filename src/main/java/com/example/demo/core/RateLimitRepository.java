package com.example.demo.core;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class RateLimitRepository {
    @Cacheable("rateLimit")
    public RateLimit getRateLimit(Client client) {
        return new RateLimit(client, new HashMap<>());
    }
}
