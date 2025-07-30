package dev.jamespagani.config;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import org.hibernate.cfg.Environment;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class DatabaseConfigManager {
    private static EntityManagerFactory entityManagerFactory;
    private static final String PERSISTANCE_UNIT_NAME = "local-db";

    /**
     * Retrieves the Entity Manager Factory
     * @return The Entity Manager Factory
     */
    public static EntityManagerFactory getEntityManagerFactory() {
        if (entityManagerFactory == null) {
            System.out.println("Initializing the Entity Manager Factory");
            initializeEntityManagerFactory();
        }
        return entityManagerFactory;
    }

    /**
     * Close the Entity Manager Factory
     */
    public static void closeEntityManagerFactory() {
        if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
            entityManagerFactory.close();
        }
    }

    private static void initializeEntityManagerFactory() {
        try {
            String dbPath = getDatabasePath();
            createDirIfNotExists(dbPath);
            Map<String, Object> properties = setHibernateProperties(dbPath);
            entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTANCE_UNIT_NAME, properties);
        } catch (Exception e) {
            throw new RuntimeException("Could not start the database: " + e.getMessage(), e);
        }

    }

    // Set the persistence properties
    private static Map<String, Object> setHibernateProperties(String dbPath) {
        Map<String, Object> properties = new HashMap<>();

        properties.put(Environment.JAKARTA_JDBC_DRIVER, "org.sqlite.JDBC");
        properties.put(Environment.JAKARTA_JDBC_URL, "jdbc:sqlite:" + dbPath);
        properties.put(Environment.JAKARTA_HBM2DDL_DATABASE_ACTION, "create");
        properties.put(Environment.CONNECTION_PROVIDER, "org.hibernate.hikaricp.internal.HikariCPConnectionProvider");

        return properties;
    }

    // Get the path
    private static String getDatabasePath() {
        String os = System.getProperty("os.name").toLowerCase();
        String userHome = System.getProperty("user.home");
        String appName = "Stockpile";
        String dbFileName = "data.db";

        // In order: Windows, Linux/Unix, macOS
        if (os.contains("win")) {
            return Path.of(userHome, "AppData", "Local", appName, dbFileName).toString();
        } else if (os.contains("linux") || os.contains("unix")) {
            return Path.of(userHome, ".stockpile", dbFileName).toString();
        } else if (os.contains("mac")) {
            return Path.of(userHome, "Library", "Application Support", appName, dbFileName).toString();
        }

        return "./inventario.db";
    }

    private static void createDirIfNotExists(String dbStringPath) {
        try {
            Path dbPath = Path.of(dbStringPath);
            Path parentDir = dbPath.getParent();

            if (parentDir != null && !Files.exists(parentDir)) {
                Files.createDirectories(parentDir);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error creating the database file", e);
        }
    }
}
