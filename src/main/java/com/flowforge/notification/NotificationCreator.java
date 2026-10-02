package com.flowforge.notification;

public abstract class NotificationCreator {

    /*
     * Factory Method.
     *
     * Subclasses decide which NotificationProduct
     * implementation should be created.
     */
    protected abstract NotificationProduct createNotification();

    /*
     * Common operation that uses the product
     * created by the Factory Method.
     */
    public NotificationProduct getNotification() {
        return createNotification();
    }
}