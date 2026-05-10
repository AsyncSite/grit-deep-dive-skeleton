package com.teamgrit.deepdive.skeleton.adapter.web;

import com.teamgrit.deepdive.skeleton.application.EventRelayService;
import com.teamgrit.deepdive.skeleton.domain.StreamEvent;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EventRelayController {

    private final EventRelayService eventRelayService;

    public EventRelayController(EventRelayService eventRelayService) {
        this.eventRelayService = eventRelayService;
    }

    @PostMapping("/streams/{stream}/events")
    public String publish(@PathVariable String stream, @RequestParam String payload) {
        return eventRelayService.publish(stream, payload);
    }

    @GetMapping("/streams/{stream}/groups/{group}/read")
    public List<StreamEvent> read(
            @PathVariable String stream,
            @PathVariable String group,
            @RequestParam(defaultValue = "worker-1") String consumer,
            @RequestParam(defaultValue = "10") int count
    ) {
        return eventRelayService.read(stream, group, consumer, count);
    }
}
