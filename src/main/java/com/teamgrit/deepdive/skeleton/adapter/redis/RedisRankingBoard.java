package com.teamgrit.deepdive.skeleton.adapter.redis;

import com.teamgrit.deepdive.skeleton.application.RankingBoard;
import com.teamgrit.deepdive.skeleton.domain.RankEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisRankingBoard implements RankingBoard {

    private final StringRedisTemplate redisTemplate;

    public RedisRankingBoard(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void recordScore(String boardId, String memberId, double score, int maxWindowSize) {
        String key = key(boardId);
        redisTemplate.opsForZSet().add(key, memberId, score);

        Long size = redisTemplate.opsForZSet().zCard(key);
        if (size != null && size > maxWindowSize) {
            long overflow = size - maxWindowSize;
            redisTemplate.opsForZSet().removeRange(key, 0, overflow - 1);
        }
    }

    @Override
    public List<RankEntry> top(String boardId, int limit) {
        Set<ZSetOperations.TypedTuple<String>> tuples =
                redisTemplate.opsForZSet().reverseRangeWithScores(key(boardId), 0, limit - 1);
        if (tuples == null) {
            return List.of();
        }

        List<RankEntry> entries = new ArrayList<>(tuples.size());
        for (ZSetOperations.TypedTuple<String> tuple : tuples) {
            entries.add(new RankEntry(tuple.getValue(), tuple.getScore()));
        }
        return entries;
    }

    @Override
    public long size(String boardId) {
        Long size = redisTemplate.opsForZSet().zCard(key(boardId));
        return size == null ? 0 : size;
    }

    private String key(String boardId) {
        return "ranking:" + boardId;
    }
}
