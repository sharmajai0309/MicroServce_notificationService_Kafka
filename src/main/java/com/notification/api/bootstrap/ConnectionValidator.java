package com.notification.api.bootstrap;


import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.notification.api.config.ApplicationProperties;
import com.notification.api.exception.ValidationException;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.ListTopicsResult;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class ConnectionValidator {

    private final RedisConnectionFactory redisConnectionFactory;
    private final MongoClient mongoClient;
    private final ApplicationProperties  applicationProperties;


    /**
     * init
     *
     */
    @PostConstruct
    private void init(){
        // Testing Redis Connection
        testRedisConnection();

        //Testing Mongo Connection
        testMongoConnection();

        //Testing Kafka Connection
        testKafkaConnection();
    }

    /**
     * test mongo connection
     *
     */
    private void testMongoConnection(){
        try{
            mongoClient.listDatabaseNames().first();
            log.info("MongoDB connection successful");
        }
        catch (Exception e){
            log.error("MongoDB connection failed", e);
            throw new RuntimeException("MongoDB not reachable", e);
        }
    }

    /**
     * test kafka connection
     *
     */
    private void testKafkaConnection(){

        try (AdminClient adminClient = AdminClient.create(
                Map.of("bootstrap.servers", applicationProperties.getKafkaServer())
        )) {
            ListTopicsResult listTopicsResult = adminClient.listTopics();
            listTopicsResult.names().get(5, TimeUnit.SECONDS);
            log.info("Kafka connection successful");
        } catch (Exception e){
            log.error("Kafka connection failed", e);
            throw new ValidationException("Kafka not reachable");
        }

//        Connection Test Using Kafka admin
        /*
        try (AdminClient adminClient =
                 AdminClient.create(kafkaAdmin.getConfigurationProperties())) {

        adminClient.listTopics().names().get(5, TimeUnit.SECONDS);

        log.info("Kafka connection successful");

    } catch (Exception e) {
        log.error("Kafka connection failed", e);
        throw new ValidationException("Kafka not reachable");
    }
         */


    }


    /**
     * test redis connection
     *
     */
    private void testRedisConnection() {
        try (var connection = redisConnectionFactory.getConnection()) {
            connection.ping();
            log.info("Redis connection successful");
        } catch (Exception e){
            log.error("Redis connection failed", e);
            throw new RuntimeException("Redis not reachable", e);
        }
    }


}
