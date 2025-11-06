package xyz.mlhmz.gaspricelog;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SQLiteTestResource implements QuarkusTestResourceLifecycleManager {
    private static final String IN_MEMORY_SQLITE_DB_URL = "jdbc:sqlite::memory:";

    @Override
    public Map<String, String> start() {
        Map<String, String> config = new HashMap<>();
        config.put("quarkus.datasource.jdbc.url", IN_MEMORY_SQLITE_DB_URL);
        return config;
    }

    @Override
    public void stop() {
    }

    @Transactional
    public static void teardown(EntityManager entityManager) {
        Query query = entityManager.createNativeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name NOT LIKE 'sqlite_%';");
        List<String> tables = query.getResultList();

        if (tables.isEmpty()) {
            System.out.println("No tables found to clear.");
            return;
        }

        for (String table : tables) {
            String dropQuery = "DELETE FROM " + table;
            entityManager.createNativeQuery(dropQuery).executeUpdate();
            System.out.println("Dropped table: " + table);
        }
    }
}
