package com.flowforge.controller;

import com.flowforge.entity.Notification;
import com.flowforge.service.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(
            NotificationService notificationService
    ) {
        this.notificationService = notificationService;
    }

    /*
     * Get notifications for a user.
     *
     * Example:
     * GET /notifications?userId=4
     */
    @GetMapping
    public ResponseEntity<List<Notification>> getNotifications(
            @RequestParam Long userId
    ) {

        return ResponseEntity.ok(
                notificationService.getNotifications(userId)
        );
    }

    /*
     * Mark a single notification as read.
     *
     * Example:
     * PUT /notifications/1/read
     */
    @PutMapping("/{id}/read")
    public ResponseEntity<Notification> markAsRead(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                notificationService.markAsRead(id)
        );
    }

    /*
     * Mark all notifications as read.
     *
     * Example:
     * PUT /notifications/read-all?userId=4
     */
    @PutMapping("/read-all")
    public ResponseEntity<Void> markAllAsRead(
            @RequestParam Long userId
    ) {

        notificationService.markAllAsRead(userId);

        return ResponseEntity.noContent().build();
    }
}