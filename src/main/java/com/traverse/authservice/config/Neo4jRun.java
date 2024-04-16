package com.traverse.authservice.config;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.Session;
import org.neo4j.driver.internal.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.data.neo4j.core.Neo4jTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Neo4jRun {

    @Autowired
    private Neo4jClient client;

    @PostConstruct
    public void createIndexesAndConstraints() {
        try {
            client.query("CREATE CONSTRAINT IF NOT EXISTS FOR (u:User) REQUIRE u.email IS UNIQUE").run();
        } catch (Exception e) {
            log.error("Exception creating uniqueness constraint for User: {}", e.getMessage());
        }
    }
}
