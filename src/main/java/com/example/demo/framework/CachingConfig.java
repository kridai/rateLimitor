package com.example.demo.framework;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;

@Configuration
@EnableCaching
@EnableScheduling
public class CachingConfig {
    public static final String RATE_LIMITOR_STORAGE = "rateLimit";
    @Bean
    public CacheManager cacheManager() {
        ConcurrentMapCacheManager cacheManager = new ConcurrentMapCacheManager(RATE_LIMITOR_STORAGE);

        return cacheManager;
    }

    @CacheEvict(allEntries = true, value = {RATE_LIMITOR_STORAGE})
    @Scheduled(cron="0 0 * * * ?")
    public void reportCacheEvict() {

        System.out.println("Flush Cache " + (new Date()).toString());
    }
}