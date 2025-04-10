package org.example.booktracker.evenListener;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.cache.CacheManager;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record CacheStats(
        CacheManager cacheManager,
        String cacheName
) {
}
