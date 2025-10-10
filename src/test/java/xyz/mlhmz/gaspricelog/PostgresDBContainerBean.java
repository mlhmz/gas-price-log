package xyz.mlhmz.gaspricelog;

import jakarta.enterprise.context.ApplicationScoped;
import org.testcontainers.containers.PostgreSQLContainer;

import java.io.IOException;
import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.fail;

@ApplicationScoped
public class PostgresDBContainerBean {
    List<String> tables = List.of(
            "entry",
            "forecastgroup",
            "group_entry",
            "group_span",
            "span",
            "valuegroup",
            "valuegroup_entry",
            "valuegroup_span"
    );

    PostgreSQLContainer<?> container;

    public PostgresDBContainerBean() {
        container = new PostgreSQLContainer<>("postgres:latest");
        container.start();
    }

    public void teardown() {
        PostgreSQLContainer<?> postgresContainer = getPostgresContainer();
        tables.forEach(tableName -> executeDeleteStatement(postgresContainer, tableName));
    }

    private void executeDeleteStatement(PostgreSQLContainer<?> postgresContainer, String tableName) {
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

    public PostgreSQLContainer<?> getPostgresContainer() {
        return container;
    }
}
