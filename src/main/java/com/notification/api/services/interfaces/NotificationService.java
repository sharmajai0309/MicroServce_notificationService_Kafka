package com.notification.api.services.interfaces;

import com.notification.api.models.request.SendNotificationRequest;

public interface NotificationService {

    public void sendNotification(final SendNotificationRequest request);
}
