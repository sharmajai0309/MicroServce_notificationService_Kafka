package com.notification.api.PubSub.Primary;

public interface GenericProvider {

    /**
     * send boolean
     *
     * @param TopicName TopicName
     * @param message message
     * @return {@link boolean}
     */
    boolean sendNotification(String TopicName,String message);
}
