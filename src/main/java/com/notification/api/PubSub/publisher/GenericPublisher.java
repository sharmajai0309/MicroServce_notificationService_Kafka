package com.notification.api.PubSub.publisher;

public interface GenericPublisher {

    void sendNotification(final String topic , final String message);
}
