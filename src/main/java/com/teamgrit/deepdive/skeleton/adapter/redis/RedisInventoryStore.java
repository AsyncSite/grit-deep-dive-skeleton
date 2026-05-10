package com.teamgrit.deepdive.skeleton.adapter.redis;

import java.time.Duration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisInventoryStore {

    private final StringRedisTemplate redisTemplate;

    public RedisInventoryStore(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void reset(String sku, int quantity) {
        redisTemplate.opsForValue().set(key(sku), String.valueOf(quantity));
    }

    public int remaining(String sku) {
        String value = redisTemplate.opsForValue().get(key(sku));
        return value == null ? 0 : Integer.parseInt(value);
    }

    public boolean reserveOneUnsafe(String sku, Duration workDelay) {
        String key = key(sku);
        int current = remaining(sku);
        if (current <= 0) {
            return false;
        }

        if (!workDelay.isZero()) {
            sleep(workDelay);
        }

        redisTemplate.opsForValue().set(key, String.valueOf(current - 1));
        return true;
    }

    private void sleep(Duration delay) {
        try {
            Thread.sleep(delay.toMillis());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("interrupted while simulating checkout work", e);
        }
    }

    private String key(String sku) {
        return "inventory:" + sku;
    }
}
