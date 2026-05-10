package com.teamgrit.deepdive.skeleton.adapter.web;

import com.teamgrit.deepdive.skeleton.application.CacheFillService;
import java.time.Duration;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CacheFillController {

    private final CacheFillService cacheFillService;

    public CacheFillController(CacheFillService cacheFillService) {
        this.cacheFillService = cacheFillService;
    }

    @PostMapping("/cache/fill")
    public TtlResponse fill(
            @RequestParam String key,
            @RequestParam String value,
            @RequestParam(defaultValue = "60") long baseTtlSeconds
    ) {
        Duration ttl = cacheFillService.fill(key, value, Duration.ofSeconds(baseTtlSeconds));
        return new TtlResponse(key, ttl.toSeconds());
    }

    public record TtlResponse(String key, long ttlSeconds) {
    }
}
