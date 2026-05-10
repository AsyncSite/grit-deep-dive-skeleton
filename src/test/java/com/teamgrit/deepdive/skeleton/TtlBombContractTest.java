package com.teamgrit.deepdive.skeleton;

import static org.assertj.core.api.Assertions.assertThat;

import com.teamgrit.deepdive.skeleton.application.CacheFillService;
import com.teamgrit.deepdive.skeleton.application.TtlPolicy;
import java.time.Duration;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

@SpringBootTest
class TtlBombContractTest {

    @Autowired
    TtlPolicy ttlPolicy;

    @Autowired
    CacheFillService cacheFillService;

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
    void ttlPolicySpreadsBaseTtlAcrossManyKeys() {
        Duration base = Duration.ofSeconds(60);

        Set<Long> ttls = IntStream.range(0, 500)
                .mapToObj(index -> ttlPolicy.ttlFor("product:" + index, base).toSeconds())
                .collect(Collectors.toSet());

        assertThat(ttls).hasSizeGreaterThan(10);
        assertThat(ttls).allSatisfy(ttl -> assertThat(ttl).isBetween(48L, 72L));
    }

    @Test
    void cacheFillWritesDifferentTtlsToRedis() {
        Duration base = Duration.ofSeconds(60);

        Set<Long> ttls = IntStream.range(0, 100)
                .mapToObj(index -> cacheFillService.fill("product:" + index, "value", base).toSeconds())
                .collect(Collectors.toSet());

        assertThat(ttls).hasSizeGreaterThan(10);
    }
}
