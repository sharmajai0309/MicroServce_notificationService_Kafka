package com.notification.api.controller.Notification;

import com.notification.api.models.request.SendNotificationRequest;
import com.notification.api.services.interfaces.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *   The type Notification controller.
 *
 */
@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
@Slf4j
public class NotificationController {


    private final NotificationService notificationService;


    /**
     * send notification
     *
     * @param sendNotificationRequest sendNotificationRequest
     * @return {@link ResponseEntity}
     * @see ResponseEntity
     * @see String
     */
    @PostMapping("/send")
    public ResponseEntity<String> sendNotification(@RequestBody SendNotificationRequest sendNotificationRequest) {
        log.info("From controller Sending..... notification request: {}", sendNotificationRequest);
       notificationService.sendNotification(sendNotificationRequest);
       return ResponseEntity.ok().body("Notification Send Successfully");

    }



}
