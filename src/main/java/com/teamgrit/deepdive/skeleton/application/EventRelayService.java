package com.teamgrit.deepdive.skeleton.application;

import com.teamgrit.deepdive.skeleton.domain.StreamEvent;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class EventRelayService {

    private final ReliableEventStream eventStream;

    public EventRelayService(ReliableEventStream eventStream) {
        this.eventStream = eventStream;
    }

    public String publish(String stream, String payload) {
        return eventStream.append(stream, payload);
    }

    public List<StreamEvent> read(String stream, String group, String consumer, int count) {
        eventStream.createGroupIfAbsent(stream, group);
        return eventStream.readNew(stream, group, consumer, count);
    }

    public void ack(String stream, String group, String messageId) {
        eventStream.ack(stream, group, messageId);
    }

    public long pendingCount(String stream, String group) {
        return eventStream.pendingCount(stream, group);
    }
}
