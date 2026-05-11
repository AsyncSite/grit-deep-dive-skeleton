package com.teamgrit.deepdive.skeleton.adapter.redis;

import com.teamgrit.deepdive.skeleton.application.ReliableEventStream;
import com.teamgrit.deepdive.skeleton.domain.StreamEvent;
import java.util.List;
import java.util.Map;
import org.springframework.data.redis.RedisSystemException;
import org.springframework.data.redis.connection.stream.Consumer;
import org.springframework.data.redis.connection.stream.PendingMessagesSummary;
import org.springframework.data.redis.connection.stream.ReadOffset;
import org.springframework.data.redis.connection.stream.RecordId;
import org.springframework.data.redis.connection.stream.StreamOffset;
import org.springframework.data.redis.connection.stream.StreamReadOptions;
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
        RecordId id = redisTemplate.opsForStream().add(stream, Map.of("payload", payload));
        if (id == null) {
            throw new IllegalStateException("Redis did not return a stream id");
        }
        return id.getValue();
    }

    @Override
    public void createGroupIfAbsent(String stream, String group) {
        try {
            redisTemplate.opsForStream().createGroup(stream, ReadOffset.from("0-0"), group);
        } catch (RedisSystemException e) {
            if (!String.valueOf(e.getMessage()).contains("BUSYGROUP")) {
                throw e;
            }
        }
    }

    @Override
    public List<StreamEvent> readNew(String stream, String group, String consumer, int count) {
        var records = redisTemplate.opsForStream().read(
                Consumer.from(group, consumer),
                StreamReadOptions.empty().count(count),
                StreamOffset.create(stream, ReadOffset.lastConsumed())
        );
        if (records == null) {
            return List.of();
        }

        return records.stream()
                .map(record -> new StreamEvent(
                        record.getId().getValue(),
                        String.valueOf(record.getValue().get("payload"))
                ))
                .toList();
    }

    @Override
    public void ack(String stream, String group, String messageId) {
        redisTemplate.opsForStream().acknowledge(stream, group, messageId);
    }

    @Override
    public long pendingCount(String stream, String group) {
        PendingMessagesSummary summary = redisTemplate.opsForStream().pending(stream, group);
        return summary.getTotalPendingMessages();
    }
}
