package com.notification.api.PubSub.publisher;


import com.notification.api.PubSub.Primary.GenericProvider;
import com.notification.api.PubSub.fallBack.GenericFallBackPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
@Service
@RequiredArgsConstructor
public class GenericPublisherImpl implements GenericPublisher {

    private List<GenericProvider> genericPublishers;

    private List<GenericFallBackPublisher> fallBackPublishers;


    @Override
    public void sendNotification(final String topic, final String message) {

        log.info("Sending notification using Generic publisher");
        AtomicBoolean success = new AtomicBoolean(false);
        genericPublishers.forEach(publisher -> {
            boolean publisherStatus = publisher.sendNotification(topic, message);
            if (publisherStatus && !success.get()) {
                success.set(true);
            }
            if (publisherStatus) {
                log.info(
                        "Successfully sent notification using topic : {} , using provider : {}",
                        topic,
                        publisher.getClass().getSimpleName()
                );
            } else {
                log.error(
                        "Error while publishing data for topic : {} , using provider : {}",
                        topic,
                        publisher.getClass().getSimpleName()
                );
            }
        });
        if (!success.get()) {
            fallBackPublishers.forEach(fallback -> {
                boolean publisherStatus = fallback.sendNotification(topic, message);
                if (publisherStatus) {
                    log.info(
                            "Notification sent to topic : {} , using fallback provider : {}",
                            topic,
                            fallback.getClass().getSimpleName()
                    );
                } else {
                    log.error(
                            "Error while publishing data for topic : {} , using fallback provider : {}",
                            topic,
                            fallback.getClass().getSimpleName()
                    );
                }
            });
        }
    }


}
