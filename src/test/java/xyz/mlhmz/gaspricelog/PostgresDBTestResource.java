package xyz.mlhmz.gaspricelog;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import org.testcontainers.containers.PostgreSQLContainer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.fail;

public class PostgresDBTestResource implements QuarkusTestResourceLifecycleManager {
    private static final List<String> TABLES = List.of(
            "entry",
            "forecastgroup",
            "group_entry",
            "group_span",
            "span",
            "valuegroup",
            "valuegroup_entry",
            "valuegroup_span"
    );

    static PostgreSQLContainer<?> postgresContainer;

    @Override
    public Map<String, String> start() {
        postgresContainer = new PostgreSQLContainer<>("postgres:latest");
        postgresContainer.start();
        Map<String, String> config = new HashMap<>();
        config.put("quarkus.datasource.jdbc.url", postgresContainer.getJdbcUrl());
        config.put("quarkus.datasource.username", postgresContainer.getUsername());
        config.put("quarkus.datasource.password", postgresContainer.getPassword());
        return config;
    }

    @Override
    public void stop() {
        postgresContainer.stop();
    }


    public static void teardown() {
        TABLES.forEach(tableName -> executeDeleteStatement(postgresContainer, tableName));
    }

    private static void executeDeleteStatement(PostgreSQLContainer<?> postgresContainer, String tableName) {
        System.out.printf("Deleting table '%s'.", tableName);
        try (Connection connection = DriverManager.getConnection(
                postgresContainer.getJdbcUrl(),
                postgresContainer.getUsername(),
                postgresContainer.getPassword());
             PreparedStatement statement = connection.prepareStatement("DELETE FROM ?;")) {
            statement.setString(0, tableName);
            statement.executeUpdate();
        } catch (SQLException exception) {
            fail("A sql exception occured while tearing down.");
        }
    }
}
