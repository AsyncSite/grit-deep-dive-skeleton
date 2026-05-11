package com.teamgrit.deepdive.skeleton.adapter.redis;

import com.teamgrit.deepdive.skeleton.application.ProductCache;
import com.teamgrit.deepdive.skeleton.domain.ProductSnapshot;
import java.math.BigDecimal;
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
        String value = redisTemplate.opsForValue().get(key(productId));
        if (value == null) {
            return Optional.empty();
        }

        String[] parts = value.split(":", 2);
        if (parts.length != 2) {
            redisTemplate.delete(key(productId));
            return Optional.empty();
        }

        return Optional.of(new ProductSnapshot(
                productId,
                Long.parseLong(parts[0]),
                new BigDecimal(parts[1])
        ));
    }

    @Override
    public void write(ProductSnapshot snapshot, Duration ttl) {
        String value = snapshot.version() + ":" + snapshot.price().toPlainString();
        redisTemplate.opsForValue().set(key(snapshot.productId()), value, ttl);
    }

    @Override
    public void evict(String productId) {
        redisTemplate.delete(key(productId));
    }

    private String key(String productId) {
        return "product:" + productId;
    }
}
