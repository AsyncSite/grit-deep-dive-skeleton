package com.teamgrit.deepdive.skeleton.adapter.redis;

import com.teamgrit.deepdive.skeleton.application.ProductCache;
import com.teamgrit.deepdive.skeleton.domain.ProductSnapshot;
import java.time.Duration;
import java.util.Optional;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisProductCache implements ProductCache {

    private final StringRedisTemplate redisTemplate;

    public RedisProductCache(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Optional<ProductSnapshot> read(String productId) {
        /*
         * TODO:
         * - Read cache key product:{id}.
         * - Parse value format "version:price".
         * - Return Optional.empty() on miss.
         */
        throw new UnsupportedOperationException("implement Redis cache read");
    }

    @Override
    public void write(ProductSnapshot snapshot, Duration ttl) {
        /*
         * TODO:
         * - Store value format "version:price".
         * - Set TTL in the same Redis write.
         */
        throw new UnsupportedOperationException("implement Redis cache write");
    }

    @Override
    public void evict(String productId) {
        /*
         * TODO:
         * - Delete only this product cache key.
         */
        throw new UnsupportedOperationException("implement Redis cache evict");
    }

    private String key(String productId) {
        return "product:" + productId;
    }
}
