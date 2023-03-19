package org.stanislav.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author Stanislav Hlova
 */
public class DatabaseConfigurer {

    private final Properties properties;

    public DatabaseConfigurer(Properties properties) {
        this.properties = properties;
        registerDriver(this.properties.getProperty("database.driver"));
    }

    public DatabaseConfigurer() {
        Properties properties = new Properties();

        try {
            properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("database.properties"));
        } catch (IOException exception) {
            System.err.println("Can't load properties.");
            throw new RuntimeException(exception);
        }
        this.properties = properties;
        registerDriver(this.properties.getProperty("database.driver"));
    }

    private void registerDriver(String driver) {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException exception) {
            System.err.println("Can't register a driver.");
            throw new RuntimeException(exception);
        }
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(properties.getProperty("database.url"),
                properties.getProperty("database.username"),
                properties.getProperty("database.password"));
    }
}
