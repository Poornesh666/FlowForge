package com.flowforge.notification;

public interface NotificationProduct {

    String getMessage(Long taskId, String details);

    String getType();
}