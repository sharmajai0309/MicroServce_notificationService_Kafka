package com.notification.api.PubSub.impl;

import com.notification.api.PubSub.interfaces.RabbitMqProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RabbitMqProviderImpl implements RabbitMqProvider {
    /**
     * @param TopicName
     * @param message
     * @return
     */
    @Override
    public boolean sendBoolean(String TopicName, String message) {
        log.info("Sending Notification message to RabbitMq Topic: {}", TopicName);
        return true;
    }
}
