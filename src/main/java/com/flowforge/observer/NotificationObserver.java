package com.flowforge.observer;

import com.flowforge.entity.Notification;
import com.flowforge.entity.Task;
import com.flowforge.entity.User;
import com.flowforge.notification.NotificationCreator;
import com.flowforge.notification.NotificationProduct;
import com.flowforge.notification.StatusChangedNotificationCreator;
import com.flowforge.notification.TaskAssignedNotificationCreator;
import com.flowforge.repository.NotificationRepository;
import com.flowforge.repository.TaskRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class NotificationObserver
        implements TaskObserver {

    private final TaskRepository taskRepository;
    private final NotificationRepository notificationRepository;

    private final StatusChangedNotificationCreator
            statusChangedCreator;

    private final TaskAssignedNotificationCreator
            taskAssignedCreator;

    public NotificationObserver(
            TaskRepository taskRepository,
            NotificationRepository notificationRepository,
            StatusChangedNotificationCreator statusChangedCreator,
            TaskAssignedNotificationCreator taskAssignedCreator
    ) {

        this.taskRepository = taskRepository;
        this.notificationRepository = notificationRepository;
        this.statusChangedCreator = statusChangedCreator;
        this.taskAssignedCreator = taskAssignedCreator;
    }

    @Override
    public void update(
            Long taskId,
            String eventType,
            String message
    ) {

        Task task = taskRepository.findById(taskId)
                .orElse(null);

        if (task == null) {
            return;
        }

        User user = task.getAssignee();

        /*
         * No notification is created if
         * the task has no assignee.
         */
        if (user == null) {
            return;
        }

        NotificationCreator creator;

        switch (eventType) {

            case "STATUS_CHANGED" ->
                    creator = statusChangedCreator;

            case "TASK_ASSIGNED" ->
                    creator = taskAssignedCreator;

            default ->
                    throw new IllegalArgumentException(
                            "Unknown event type: " + eventType
                    );
        }

        /*
         * Factory Method is invoked here.
         */
        NotificationProduct product =
                creator.getNotification();

        Notification notification =
                new Notification();

        notification.setUser(user);

        notification.setType(
                product.getType()
        );

        notification.setMessage(
                product.getMessage(
                        taskId,
                        message
                )
        );

        notification.setRead(false);

        notification.setCreatedAt(
                LocalDateTime.now()
        );

        notificationRepository.save(notification);
    }
}