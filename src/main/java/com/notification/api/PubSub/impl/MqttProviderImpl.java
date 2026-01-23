package com.notification.api.PubSub.impl;

import com.notification.api.PubSub.interfaces.MqttProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@ConditionalOnProperty(value = "messaging.providers.MQTT.enabled" , havingValue = "true")
public class MqttProviderImpl implements MqttProvider {
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
