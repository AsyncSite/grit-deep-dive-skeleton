package com.teamgrit.deepdive.skeleton.adapter.policy;

import com.teamgrit.deepdive.skeleton.application.TtlPolicy;
import java.time.Duration;
import org.springframework.stereotype.Component;

@Component
public class JitteredTtlPolicy implements TtlPolicy {

    @Override
    public Duration ttlFor(String key, Duration baseTtl) {
        /*
         * TODO:
         * - Spread TTL between 80% and 120% of baseTtl.
         * - Prefer deterministic jitter from key hash so repeated writes for
         *   the same key do not move unpredictably.
         * - Never return zero or negative TTL.
         */
        throw new UnsupportedOperationException("implement jittered TTL policy");
    }
}
