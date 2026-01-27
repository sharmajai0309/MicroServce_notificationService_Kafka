package com.notification.api.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class ApplicationProperties {

    @Value("${kafka.audit.topic}")
    private  String auditTopic;

    @Value("${kafka.ingest.topic}")
    private String ingestTopic;
}
