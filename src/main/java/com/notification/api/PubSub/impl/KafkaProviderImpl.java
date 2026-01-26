package com.notification.api.PubSub.impl;

import com.notification.api.PubSub.interfaces.KafkaProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@ConditionalOnProperty(value = "messaging.providers.Kafka.enabled" , havingValue = "true")
public class KafkaProviderImpl implements KafkaProvider {

    private final KafkaTemplate<String,String> kafkaTemplate;
    /**
     * @param topicName
     * @param message
     * @return
     */


    @Override
    public boolean sendNotification(String topicName, String message) {
        log.info("In Service Impl layer");

        log.info("Sending received params for validations");
        validate(topicName, message);
        try {
            log.info("in Try block.... Sending  message to topic {}", topicName);
            kafkaTemplate.send(topicName, message);
            return true;
        } catch (Exception e) {
            log.error("Error sending Kafka message", e);
            return false;
        }
    }
    private void validate(String topicName, String message) {
        if (topicName == null || topicName.trim().isEmpty())
            throw new IllegalArgumentException("Topic name must not be empty");

        if (message == null || message.trim().isEmpty())
            throw new IllegalArgumentException("Message must not be empty");
    }

}
