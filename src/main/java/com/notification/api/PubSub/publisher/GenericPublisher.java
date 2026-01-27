package com.notification.api.PubSub.publisher;

public interface GenericPublisher {

    void sendDataToIngest(Object input);

    void sendDataToAudit(Object input);

    void sendNotification(final String topic , final String message);
}
