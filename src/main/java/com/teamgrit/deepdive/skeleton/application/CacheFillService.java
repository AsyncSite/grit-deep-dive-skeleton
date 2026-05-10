package com.teamgrit.deepdive.skeleton.application;

import com.teamgrit.deepdive.skeleton.adapter.redis.RedisCacheWriter;
import java.time.Duration;
import org.springframework.stereotype.Service;

@Service
public class CacheFillService {

    private final TtlPolicy ttlPolicy;
    private final RedisCacheWriter cacheWriter;

    public CacheFillService(TtlPolicy ttlPolicy, RedisCacheWriter cacheWriter) {
        this.ttlPolicy = ttlPolicy;
        this.cacheWriter = cacheWriter;
    }

    public Duration fill(String key, String value, Duration baseTtl) {
        Duration ttl = ttlPolicy.ttlFor(key, baseTtl);
        cacheWriter.write(key, value, ttl);
        return ttl;
    }
}
