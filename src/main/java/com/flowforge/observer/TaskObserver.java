package com.flowforge.observer;

public interface TaskObserver {

    void update(
            Long taskId,
            String eventType,
            String message
    );
}