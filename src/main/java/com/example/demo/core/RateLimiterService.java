package com.example.demo.core;

public interface RateLimiterService {
    default boolean limitReached(String uri, String client) {
        return true;
    }
    default  boolean updateLimit(String clientName, String uri) {
        return false;
    }
}
