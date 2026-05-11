package com.teamgrit.deepdive.skeleton.adapter.policy;

import com.teamgrit.deepdive.skeleton.application.TtlPolicy;
import java.time.Duration;
import org.springframework.stereotype.Component;

@Component
public class JitteredTtlPolicy implements TtlPolicy {

    @Override
    public Duration ttlFor(String key, Duration baseTtl) {
        long baseMillis = Math.max(1, baseTtl.toMillis());
        long minMillis = Math.max(1, baseMillis * 80 / 100);
        long maxMillis = Math.max(minMillis, baseMillis * 120 / 100);
        long spread = maxMillis - minMillis;
        long offset = spread == 0 ? 0 : mixedHash(key) % (spread + 1);

        return Duration.ofMillis(minMillis + offset);
    }

    private long mixedHash(String key) {
        int hash = key.hashCode();
        hash ^= hash >>> 16;
        hash *= 0x7feb352d;
        hash ^= hash >>> 15;
        hash *= 0x846ca68b;
        hash ^= hash >>> 16;
        return Integer.toUnsignedLong(hash);
    }
}
