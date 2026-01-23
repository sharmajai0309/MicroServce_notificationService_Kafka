package com.notification.api.PubSub.impl;

import com.notification.api.PubSub.interfaces.AwsSqsProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@ConditionalOnProperty(value = "messaging.providers.aws.enabled" , havingValue = "true")
public class AwsSqsProviderImpl implements AwsSqsProvider {
    /**
     * @param TopicName
     * @param message
     * @return
     */
    @Override
    public boolean sendBoolean(String TopicName, String message) {
        log.info("Sending Notification message to AWS Sqs Topic: {}", TopicName);
        return true;
    }
}
