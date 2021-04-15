package com.example.demo.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.Stack;


@Service
public class RateLimiterServiceImpl implements RateLimiterService {
    Logger logger = LoggerFactory.getLogger(RateLimiterServiceImpl.class);
    @Autowired
    RateLimitRepository rateLimitRepository;
    @Value("${api.hourly.rate.limit}")
    Integer rateLimit;
    @Override
    public boolean limitReached(String uri, String clientName) {
        Client client = new Client(clientName);
        return rateLimit <=getLimit(client, uri);
    }
    public boolean updateLimit(String clientName, String uri) {
        Client client = new Client(clientName);
        URI val;
        try {
            val = new URI(uri);
        }catch (URISyntaxException e) {
            return false;
        }
         updateLimit(client,val);
        return true;
    }
    public int getLimit(Client client, String uri) {
        URI val;
        try {
             val = new URI(uri);
        }catch (URISyntaxException e) {
            return -1;
        }
        return getLimit(client,val);
    }
    public int getLimit(Client client, URI uri) {
        RateLimit rateLimit = rateLimitRepository.getRateLimit(client);
        if(rateLimit.getClient()!= null && rateLimit.getUriAccessMap().get(uri) != null) {
            if(rateLimit.getUriAccessMap().get(uri).peek() != null) {
                if( rateLimit.getUriAccessMap().get(uri).peek().getHour() == LocalDateTime.now().getHour()) {
                    return rateLimit.getUriAccessMap().get(uri).size();
                }
            }
        }
        return -1;
    }

    public void updateLimit(Client client, URI uri) {
        RateLimit rateLimit = rateLimitRepository.getRateLimit(client);
        rateLimit.getUriAccessMap().computeIfAbsent(uri, k -> new Stack<>());
        rateLimit.getUriAccessMap().get(uri).add(LocalDateTime.now());
        logger.info("updated rate limit for client "+ client.toString() + "value set "+ rateLimit.getUriAccessMap().get(uri).size());
    }
}
