package xyz.mlhmz.gaspricelog;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.HashMap;
import java.util.Map;

public class PostgresDBTestResource implements QuarkusTestResourceLifecycleManager {
    PostgreSQLContainer<?> container;

    @Override
    public Map<String, String> start() {
        container = new PostgreSQLContainer<>("postgres:latest");
        Map<String, String> config = new HashMap<>();
        config.put("quarkus.datasource.jdbc.url", container.getJdbcUrl());
        config.put("quarkus.datasource.username", container.getUsername());
        config.put("quarkus.datasource.password", container.getPassword());
        return config;
    }

    @Override
    public void stop() {
        container.stop();
    }
}
