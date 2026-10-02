package com.flowforge.observer;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TaskEventSubject {

    private final List<TaskObserver> observers;

    public TaskEventSubject(List<TaskObserver> observers) {
        this.observers = new ArrayList<>(observers);
    }

    public void addObserver(TaskObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(TaskObserver observer) {
        observers.remove(observer);
    }

    public void notifyObservers(
            Long taskId,
            String eventType,
            String message
    ) {

        for (TaskObserver observer : observers) {

            observer.update(
                    taskId,
                    eventType,
                    message
            );
        }
    }
}