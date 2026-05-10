package com.teamgrit.deepdive.skeleton.domain;

public record LockLease(
        String key,
        String token,
        long ttlMillis,
        long acquiredAtMillis
) {
}
