package com.flowforge.notification;

import org.springframework.stereotype.Component;

@Component
public class TaskAssignedNotificationCreator
        extends NotificationCreator {

    @Override
    protected NotificationProduct createNotification() {

        return new TaskAssignedNotification();
    }
}