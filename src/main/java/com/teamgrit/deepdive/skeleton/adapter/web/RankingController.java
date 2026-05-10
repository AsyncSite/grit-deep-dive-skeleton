package com.teamgrit.deepdive.skeleton.adapter.web;

import com.teamgrit.deepdive.skeleton.application.RankingService;
import com.teamgrit.deepdive.skeleton.domain.RankEntry;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RankingController {

    private final RankingService rankingService;

    public RankingController(RankingService rankingService) {
        this.rankingService = rankingService;
    }

    @PostMapping("/rankings/{boardId}/scores")
    public void recordScore(
            @PathVariable String boardId,
            @RequestParam String memberId,
            @RequestParam double score
    ) {
        rankingService.recordScore(boardId, memberId, score);
    }

    @GetMapping("/rankings/{boardId}/top")
    public List<RankEntry> top(@PathVariable String boardId, @RequestParam(defaultValue = "10") int limit) {
        return rankingService.top(boardId, limit);
    }
}
