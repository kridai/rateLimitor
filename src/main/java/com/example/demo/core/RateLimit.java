package com.example.demo.core;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Stack;

public class RateLimit {
    Logger logger = LoggerFactory.getLogger(RateLimit.class);
    private Client client;
    private HashMap<URI, Stack<LocalDateTime>> uriAccessMap;

    public RateLimit(Client client, HashMap<URI, Stack<LocalDateTime>> uriLimit) {
        this.client = client;
        this.uriAccessMap = uriLimit;
    }
    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public HashMap<URI, Stack<LocalDateTime>> getUriAccessMap() {
        return uriAccessMap;
    }

    public void setUriAccessMap(HashMap<URI, Stack<LocalDateTime>> uriAccessMap) {
        this.uriAccessMap = uriAccessMap;
    }
}
