package com.example.demo.controller;

import com.example.demo.framework.RateLimiter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

    @RequestMapping("/api/greeting")
    @RateLimiter
    public ResponseEntity greeting() {

        return ResponseEntity.ok("Greetings from Kridai!. Api accessible");
    }

}