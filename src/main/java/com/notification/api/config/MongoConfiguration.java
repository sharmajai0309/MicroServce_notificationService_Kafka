package com.notification.api.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import lombok.RequiredArgsConstructor;
import org.bson.UuidRepresentation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;

import java.util.concurrent.TimeUnit;

/**
 *   mongo config
 *
 */

/**
 * mongo config
 *
 */
@Configuration
@RequiredArgsConstructor
public class MongoConfiguration {
    private final ApplicationProperties applicationProperties;


    @Bean
    public MongoClient mongoClient() {
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(applicationProperties.getMongoConnection()))
                .applyToSocketSettings(builder ->
                        builder.connectTimeout(5, TimeUnit.SECONDS) //Max time to establish connection.
                                .readTimeout(5, TimeUnit.SECONDS))    // Max time to wait for response.
                .applyToClusterSettings(builder ->
                        builder.serverSelectionTimeout(5, TimeUnit.SECONDS))
                .uuidRepresentation(UuidRepresentation.STANDARD) // Used for UUID Error fix
                .build();

        return MongoClients.create(settings);
    }

    /**
     * Mongo database factory mongo database factory.
     *
     * @param mongoClient the mongo client
     * @return the mongo database factory
     */
    @Bean
    public MongoDatabaseFactory mongoDatabaseFactory(MongoClient mongoClient) {
        return new SimpleMongoClientDatabaseFactory(
                mongoClient,
                "notification_service"
        );
    }

    /**
     * Mongo template mongo template.
     *
     * @param factory the factory
     * @return the mongo template
     */
    @Bean
    public MongoTemplate mongoTemplate(MongoDatabaseFactory factory) {
        return new MongoTemplate(factory);
    }
}

