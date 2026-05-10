package com.teamgrit.deepdive.skeleton.application;

import com.teamgrit.deepdive.skeleton.domain.RankEntry;
import java.util.List;

public interface RankingBoard {

    void recordScore(String boardId, String memberId, double score, int maxWindowSize);

    List<RankEntry> top(String boardId, int limit);

    long size(String boardId);
}
