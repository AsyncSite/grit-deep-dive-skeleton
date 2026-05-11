package com.teamgrit.deepdive.skeleton.adapter.redis;

import com.teamgrit.deepdive.skeleton.application.DistributedLock;
import com.teamgrit.deepdive.skeleton.domain.LockLease;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisDistributedLock implements DistributedLock {

    private static final DefaultRedisScript<Long> RELEASE_SCRIPT = new DefaultRedisScript<>(
            """
            if redis.call('get', KEYS[1]) == ARGV[1] then
              return redis.call('del', KEYS[1])
            end
            return 0
            """,
            Long.class
    );

    private final StringRedisTemplate redisTemplate;

    public RedisDistributedLock(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Optional<LockLease> acquire(String name, Duration ttl) {
        String key = key(name);
        String token = UUID.randomUUID().toString();

        Boolean acquired = redisTemplate.opsForValue().setIfAbsent(key, token, ttl);
        if (!Boolean.TRUE.equals(acquired)) {
            return Optional.empty();
        }

        return Optional.of(new LockLease(
                key,
                token,
                ttl.toMillis(),
                Instant.now().toEpochMilli()
        ));
    }

    @Override
    public boolean release(LockLease lease) {
        Long deleted = redisTemplate.execute(RELEASE_SCRIPT, List.of(lease.key()), lease.token());
        return Long.valueOf(1).equals(deleted);
    }

    private String key(String name) {
        return "lock:" + name;
    }
}
