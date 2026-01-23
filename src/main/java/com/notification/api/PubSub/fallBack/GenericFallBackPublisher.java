package com.notification.api.PubSub.fallBack;

public interface GenericFallBackPublisher {

    boolean sendBoolean(String TopicName,String message);
}
