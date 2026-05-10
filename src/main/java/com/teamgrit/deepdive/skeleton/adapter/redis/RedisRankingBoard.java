package com.teamgrit.deepdive.skeleton.adapter.redis;

import com.teamgrit.deepdive.skeleton.application.RankingBoard;
import com.teamgrit.deepdive.skeleton.domain.RankEntry;
import java.util.List;
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
        /*
         * TODO:
         * - ZADD board member score.
         * - Keep only top maxWindowSize members.
         * - Trim low scores, not high scores.
         */
        throw new UnsupportedOperationException("implement bounded ZSET score update");
    }

    @Override
    public List<RankEntry> top(String boardId, int limit) {
        /*
         * TODO:
         * - Read highest scores first.
         * - Preserve score values.
         */
        throw new UnsupportedOperationException("implement ZSET top read");
    }

    @Override
    public long size(String boardId) {
        /*
         * TODO:
         * - Return ZCARD for this board.
         */
        throw new UnsupportedOperationException("implement ZSET size");
    }

    private String key(String boardId) {
        return "ranking:" + boardId;
    }
}
