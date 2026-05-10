package com.teamgrit.deepdive.skeleton.application;

import com.teamgrit.deepdive.skeleton.domain.StreamEvent;
import java.util.List;

public interface ReliableEventStream {

    String append(String stream, String payload);

    void createGroupIfAbsent(String stream, String group);

    List<StreamEvent> readNew(String stream, String group, String consumer, int count);

    void ack(String stream, String group, String messageId);

    long pendingCount(String stream, String group);
}
