package com.teamgrit.deepdive.skeleton.adapter.redis;

import com.teamgrit.deepdive.skeleton.application.ReliableEventStream;
import com.teamgrit.deepdive.skeleton.domain.StreamEvent;
import java.util.List;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisReliableEventStream implements ReliableEventStream {

    private final StringRedisTemplate redisTemplate;

    public RedisReliableEventStream(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public String append(String stream, String payload) {
        /*
         * TODO:
         * - Use XADD.
         * - Store payload field.
         * - Return Redis stream message id.
         */
        throw new UnsupportedOperationException("implement Redis Streams append");
    }

    @Override
    public void createGroupIfAbsent(String stream, String group) {
        /*
         * TODO:
         * - Use XGROUP CREATE.
         * - Treat BUSYGROUP as success.
         */
        throw new UnsupportedOperationException("implement consumer group creation");
    }

    @Override
    public List<StreamEvent> readNew(String stream, String group, String consumer, int count) {
        /*
         * TODO:
         * - Read new messages with consumer group.
         * - Do not ack inside read.
         */
        throw new UnsupportedOperationException("implement consumer group read");
    }

    @Override
    public void ack(String stream, String group, String messageId) {
        /*
         * TODO:
         * - XACK the processed message.
         */
        throw new UnsupportedOperationException("implement stream ack");
    }

    @Override
    public long pendingCount(String stream, String group) {
        /*
         * TODO:
         * - Return XPENDING total count.
         */
        throw new UnsupportedOperationException("implement pending count");
    }
}
