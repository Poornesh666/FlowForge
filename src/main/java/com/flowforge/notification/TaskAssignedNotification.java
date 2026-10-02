package com.flowforge.notification;

public class TaskAssignedNotification
        implements NotificationProduct {

    @Override
    public String getMessage(
            Long taskId,
            String details
    ) {

        return "Task #" + taskId +
                " has been assigned to you. " +
                details;
    }

    @Override
    public String getType() {
        return "TASK_ASSIGNED";
    }
}