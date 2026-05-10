package com.teamgrit.deepdive.skeleton.adapter.redis;

import com.teamgrit.deepdive.skeleton.application.DistributedLock;
import com.teamgrit.deepdive.skeleton.domain.LockLease;
import java.time.Duration;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisDistributedLock implements DistributedLock {

    private final StringRedisTemplate redisTemplate;

    public RedisDistributedLock(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Optional<LockLease> acquire(String name, Duration ttl) {
        String key = key(name);
        String token = UUID.randomUUID().toString();

        /*
         * TODO:
         * - Use one Redis command: SET key token NX PX ttl.
         * - Return Optional.of(new LockLease(...)) only when Redis replies OK.
         * - Return Optional.empty() when another owner already holds the lock.
         */
        throw new UnsupportedOperationException("implement Redis SET NX PX acquire for " + key);
    }

    @Override
    public boolean release(LockLease lease) {
        /*
         * TODO:
         * - Use Lua so GET + DEL is atomic.
         * - Delete the key only when the stored token equals lease.token().
         * - Return false when the key expired or another owner holds it.
         */
        throw new UnsupportedOperationException("implement token-checked Lua release");
    }

    private String key(String name) {
        return "lock:" + name;
    }
}
