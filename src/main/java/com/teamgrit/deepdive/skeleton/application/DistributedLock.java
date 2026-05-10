package com.teamgrit.deepdive.skeleton.application;

import com.teamgrit.deepdive.skeleton.domain.LockLease;
import java.time.Duration;
import java.util.Optional;

public interface DistributedLock {

    Optional<LockLease> acquire(String name, Duration ttl);

    boolean release(LockLease lease);
}
