package com.notification.api.PubSub.fallBack;

public interface GenericFallBackPublisher {

    boolean sendNotification(String TopicName,String message);
}
