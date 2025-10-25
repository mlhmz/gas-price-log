package xyz.mlhmz.gaspricelog;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import org.testcontainers.containers.PostgreSQLContainer;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.fail;

public class PostgresDBTestResource implements QuarkusTestResourceLifecycleManager {
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
        clearAllTables(postgresContainer);
    }

    private static void clearAllTables(PostgreSQLContainer<?> postgresContainer) {
        try (Connection connection = DriverManager.getConnection(
                postgresContainer.getJdbcUrl(),
                postgresContainer.getUsername(),
                postgresContainer.getPassword());
             Statement statement = connection.createStatement()) {
            DatabaseMetaData metaData = connection.getMetaData();
            try (ResultSet rs = metaData.getTables(null, null, "%", new String[]{"TABLE"})) {
                while (rs.next()) {
                    String tableName = rs.getString("TABLE_NAME");
                    System.out.printf("Clearing the table '%s'.%n", tableName);
                    String update = String.format("DELETE FROM %s;", tableName);
                    System.out.printf("Executing the statement '%s'.%n", statement);
                    statement.execute(update);
                }
            }
        } catch (SQLException exception) {
            fail("A sql exception occured while tearing down.", exception);
        }
    }
}
