package org.example.booktracker.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.booktracker.evenListener.CacheStats;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Slf4j
@Component
@RequiredArgsConstructor
public class CacheStatsService {
    final Logger logger = Logger.getLogger(CacheStatsService.class.getName());
    private final CacheManager cacheManager;

    @Scheduled(cron = "0 * * * * *")
    public void process() {
        for (String cacheName : cacheManager.getCacheNames()) {
            CacheStats cacheStats = new CacheStats(cacheManager, cacheName);
            logCacheStats(cacheStats);
        }
    }

    private void logCacheStats(CacheStats event) {
        var cache = event.cacheManager().getCache(event.cacheName());
        if (cache instanceof CaffeineCache caffeineCache) {
            var nativeCache = caffeineCache.getNativeCache();
            logger.info(() -> "Caffeine cash stats: %s, %s".formatted(caffeineCache.getName(), nativeCache.stats().toString()));
        } else {
            logger.warning(() -> "Cash not is Caffeine Cash %s".formatted(event.cacheName()));
        }
    }
}
