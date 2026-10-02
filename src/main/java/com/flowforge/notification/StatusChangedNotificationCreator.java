package com.flowforge.notification;

import org.springframework.stereotype.Component;

@Component
public class StatusChangedNotificationCreator
        extends NotificationCreator {

    @Override
    protected NotificationProduct createNotification() {

        return new StatusChangedNotification();
    }
}