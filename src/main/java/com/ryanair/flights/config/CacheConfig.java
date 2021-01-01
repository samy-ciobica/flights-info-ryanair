package com.ryanair.flights.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CacheConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(CacheConfig.class);

    @Scheduled(fixedDelay = 1800000)
    @CacheEvict(value = Constants.ROUTES_CACHE)
    public void clearRoutesCache() {
    }

    @Scheduled(fixedDelay = 1800000)
    @CacheEvict(value =  Constants.SCHEDULE_CACHE)
    public void clearScheduleCache() {
    }

}
