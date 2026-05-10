package com.teamgrit.deepdive.skeleton;

import static org.assertj.core.api.Assertions.assertThat;

import com.teamgrit.deepdive.skeleton.application.RankingService;
import com.teamgrit.deepdive.skeleton.domain.RankEntry;
import java.util.List;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

@SpringBootTest
class ZsetExplosionContractTest {

    @Autowired
    RankingService rankingService;

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
    void topReturnsMembersByDescendingScore() {
        rankingService.recordScore("daily", "alice", 10);
        rankingService.recordScore("daily", "bob", 30);
        rankingService.recordScore("daily", "chris", 20);

        List<RankEntry> top = rankingService.top("daily", 3);

        assertThat(top).extracting(RankEntry::memberId)
                .containsExactly("bob", "chris", "alice");
    }

    @Test
    void scoreUpdateKeepsTopWindowBounded() {
        IntStream.range(0, 500)
                .forEach(index -> rankingService.recordScore("daily", "user-" + index, index));

        assertThat(rankingService.size("daily")).isLessThanOrEqualTo(100);
        assertThat(rankingService.top("daily", 1).getFirst().memberId()).isEqualTo("user-499");
    }
}
