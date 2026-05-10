package com.teamgrit.deepdive.skeleton.application;

import java.time.Duration;

public interface TtlPolicy {

    Duration ttlFor(String key, Duration baseTtl);
}
