package com.notification.api.PubSub.publisher;


import com.notification.api.PubSub.fallBack.GenericFallBackPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenericPublisherImpl implements GenericPublisher {

    private List<GenericPublisher> genericPublishers;

    private List<GenericFallBackPublisher> fallBackPublishers;

    /**
     *
     */
    @Override
    public void sendNotification() {

    }
}
