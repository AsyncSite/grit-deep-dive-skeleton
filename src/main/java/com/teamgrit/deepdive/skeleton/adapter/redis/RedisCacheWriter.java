package com.teamgrit.deepdive.skeleton.adapter.redis;

import java.time.Duration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisCacheWriter {

    private final StringRedisTemplate redisTemplate;

    public RedisCacheWriter(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void write(String key, String value, Duration ttl) {
        redisTemplate.opsForValue().set(cacheKey(key), value, ttl);
    }

    public Duration ttl(String key) {
        Long seconds = redisTemplate.getExpire(cacheKey(key));
        return seconds == null || seconds < 0 ? Duration.ZERO : Duration.ofSeconds(seconds);
    }

    private String cacheKey(String key) {
        return "cache:" + key;
    }
}
