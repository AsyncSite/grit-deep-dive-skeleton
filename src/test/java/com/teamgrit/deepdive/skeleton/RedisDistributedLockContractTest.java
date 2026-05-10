package com.teamgrit.deepdive.skeleton;

import static org.assertj.core.api.Assertions.assertThat;

import com.teamgrit.deepdive.skeleton.adapter.redis.RedisDistributedLock;
import com.teamgrit.deepdive.skeleton.adapter.redis.RedisInventoryStore;
import com.teamgrit.deepdive.skeleton.application.CheckoutService;
import com.teamgrit.deepdive.skeleton.domain.LockLease;
import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

@SpringBootTest
class RedisDistributedLockContractTest {

    @Autowired
    RedisDistributedLock lock;

    @Autowired
    RedisInventoryStore inventoryStore;

    @Autowired
    CheckoutService checkoutService;

    @Autowired
    StringRedisTemplate redisTemplate;

    @BeforeEach
    void resetRedis() {
        try {
            redisTemplate.getConnectionFactory().getConnection().serverCommands().flushDb();
        } catch (RuntimeException e) {
            Assumptions.abort("Redis is not running. Run `docker compose up -d redis` first.");
        }
    }

    @Test
    void acquireBlocksSecondOwner() {
        Optional<LockLease> first = lock.acquire("inventory:limited", Duration.ofSeconds(1));

        assertThat(first).isPresent();
        assertThat(lock.acquire("inventory:limited", Duration.ofSeconds(1))).isEmpty();
    }

    @Test
    void releaseChecksTokenBeforeDeleting() {
        LockLease lease = lock.acquire("inventory:limited", Duration.ofSeconds(1)).orElseThrow();
        LockLease wrongLease = new LockLease(
                lease.key(),
                "wrong-token",
                lease.ttlMillis(),
                lease.acquiredAtMillis()
        );

        assertThat(lock.release(wrongLease)).isFalse();
        assertThat(redisTemplate.hasKey(lease.key())).isTrue();
        assertThat(lock.release(lease)).isTrue();
        assertThat(redisTemplate.hasKey(lease.key())).isFalse();
    }

    @Test
    void concurrentCheckoutAllowsExactlyOneSuccess() throws Exception {
        String sku = "limited";
        inventoryStore.reset(sku, 1);

        try (var executor = Executors.newFixedThreadPool(50)) {
            List<Boolean> results = executor.invokeAll(
                            IntStream.range(0, 100)
                                    .mapToObj(index -> (java.util.concurrent.Callable<Boolean>) () ->
                                            checkoutService.reserveOne(
                                                    sku,
                                                    Duration.ofMillis(500),
                                                    Duration.ofMillis(10)
                                            )
                                    )
                                    .toList()
                    )
                    .stream()
                    .map(future -> {
                        try {
                            return future.get();
                        } catch (Exception e) {
                            throw new IllegalStateException(e);
                        }
                    })
                    .toList();

            assertThat(results).containsOnlyOnce(true);
        }

        assertThat(inventoryStore.remaining(sku)).isZero();
    }
}
