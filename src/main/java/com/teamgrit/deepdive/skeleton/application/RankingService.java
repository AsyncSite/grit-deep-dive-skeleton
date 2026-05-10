package com.teamgrit.deepdive.skeleton.application;

import com.teamgrit.deepdive.skeleton.domain.RankEntry;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class RankingService {

    private static final int DEFAULT_WINDOW_SIZE = 100;

    private final RankingBoard rankingBoard;

    public RankingService(RankingBoard rankingBoard) {
        this.rankingBoard = rankingBoard;
    }

    public void recordScore(String boardId, String memberId, double score) {
        rankingBoard.recordScore(boardId, memberId, score, DEFAULT_WINDOW_SIZE);
    }

    public List<RankEntry> top(String boardId, int limit) {
        return rankingBoard.top(boardId, limit);
    }

    public long size(String boardId) {
        return rankingBoard.size(boardId);
    }
}
