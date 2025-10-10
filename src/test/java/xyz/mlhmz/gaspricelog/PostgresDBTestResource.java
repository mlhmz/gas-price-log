package xyz.mlhmz.gaspricelog;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import jakarta.inject.Inject;
import org.testcontainers.containers.PostgreSQLContainer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.fail;

public class PostgresDBTestResource implements QuarkusTestResourceLifecycleManager {
    @Inject
    PostgresDBContainerBean postgresDBContainerBean;

    @Override
    public Map<String, String> start() {
        PostgreSQLContainer<?> postgresContainer = getPostgresContainer();
        Map<String, String> config = new HashMap<>();
        config.put("quarkus.datasource.jdbc.url", postgresContainer.getJdbcUrl());
        config.put("quarkus.datasource.username", postgresContainer.getUsername());
        config.put("quarkus.datasource.password", postgresContainer.getPassword());
        return config;
    }

    @Override
    public void stop() {
        getPostgresContainer().stop();
    }

    private PostgreSQLContainer<?> getPostgresContainer() {
        return postgresDBContainerBean.getPostgresContainer();
    }
}
