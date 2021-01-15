package com.catan.infrastructure.persistence.mongo;

import com.mongodb.MongoClientSettings;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class MongoDBClient {

    private static String mongoHost = "localhost";
    private static String database = "catan";
    private static int mongoPort = 27017;

    @Bean
    public MongoDatabase getCatanDatabase() {
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyToClusterSettings(builder ->
                        builder.hosts(Arrays.asList(new ServerAddress(mongoHost, mongoPort))))
                .build();
        MongoClient mongoClient = MongoClients.create(settings);

        MongoDatabase database = mongoClient.getDatabase(MongoDBClient.database);
        return database;
    }
}
