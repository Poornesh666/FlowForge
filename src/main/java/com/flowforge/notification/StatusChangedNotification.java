package com.flowforge.notification;

public class StatusChangedNotification
        implements NotificationProduct {

    @Override
    public String getMessage(
            Long taskId,
            String details
    ) {

        return "Task #" + taskId +
                " status changed. " +
                details;
    }

    @Override
    public String getType() {
        return "STATUS_CHANGED";
    }
}