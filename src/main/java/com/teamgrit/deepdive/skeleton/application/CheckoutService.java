package com.teamgrit.deepdive.skeleton.application;

import com.teamgrit.deepdive.skeleton.adapter.redis.RedisInventoryStore;
import com.teamgrit.deepdive.skeleton.domain.LockLease;
import java.time.Duration;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class CheckoutService {

    private final DistributedLock distributedLock;
    private final RedisInventoryStore inventoryStore;

    public CheckoutService(DistributedLock distributedLock, RedisInventoryStore inventoryStore) {
        this.distributedLock = distributedLock;
        this.inventoryStore = inventoryStore;
    }

    public boolean reserveOne(String sku, Duration ttl, Duration workDelay) {
        Optional<LockLease> lease = distributedLock.acquire("inventory:" + sku, ttl);
        if (lease.isEmpty()) {
            return false;
        }

        try {
            return inventoryStore.reserveOneUnsafe(sku, workDelay);
        } finally {
            distributedLock.release(lease.get());
        }
    }
}
