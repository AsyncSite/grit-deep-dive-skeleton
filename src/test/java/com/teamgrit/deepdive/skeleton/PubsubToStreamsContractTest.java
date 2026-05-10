package com.teamgrit.deepdive.skeleton;

import static org.assertj.core.api.Assertions.assertThat;

import com.teamgrit.deepdive.skeleton.application.EventRelayService;
import com.teamgrit.deepdive.skeleton.domain.StreamEvent;
import java.util.List;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

@SpringBootTest
class PubsubToStreamsContractTest {

    @Autowired
    EventRelayService eventRelayService;

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
    void messageCanBeReadThroughConsumerGroup() {
        String id = eventRelayService.publish("orders", "order-created:1");

        List<StreamEvent> events = eventRelayService.read("orders", "billing", "worker-1", 10);

        assertThat(events).extracting(StreamEvent::id).contains(id);
        assertThat(events).extracting(StreamEvent::payload).contains("order-created:1");
    }

    @Test
    void messageStaysPendingUntilAck() {
        eventRelayService.publish("orders", "order-created:2");
        StreamEvent event = eventRelayService.read("orders", "billing", "worker-1", 10).getFirst();

        assertThat(eventRelayService.pendingCount("orders", "billing")).isEqualTo(1);

        eventRelayService.ack("orders", "billing", event.id());

        assertThat(eventRelayService.pendingCount("orders", "billing")).isZero();
    }
}
