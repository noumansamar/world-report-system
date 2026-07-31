package org.example.db;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
public class DatabaseConnection
{
    private static final String CONFIG_FILE = "db.properties";
    private final String url;
    private final String username;
    private final String password;
    private Connection connection;
    public DatabaseConnection()
    {
        Properties props = loadProperties();
        // Environment variables take priority over db.properties, so the
        // same JAR/image works both locally (IntelliJ) and in Docker
        // without any code changes or rebuilds.
        String host = getConfig("DB_HOST", props, "db.host", "localhost");
        String port = getConfig("DB_PORT", props, "db.port", "3306");
        String name = getConfig("DB_NAME", props, "db.name", "world");
        this.url = "jdbc:mysql://" + host + ":" + port + "/" + name + "?useSSL=false&serverTimezone=UTC";
        this.username = getConfig("DB_USERNAME", props, "db.username", "root");
        this.password = getConfig("DB_PASSWORD", props, "db.password", "");
    }

    // Checks an environment variable first, then falls back to the
    // properties file value, then finally to a hardcoded default.
    private String getConfig(String envVar, Properties props, String propKey, String defaultValue)
    {
        String envValue = System.getenv(envVar);
        if (envValue != null && !envValue.isBlank())
        {
            return envValue;
        }
        return props.getProperty(propKey, defaultValue);
    }
    private Properties loadProperties()
    {
        Properties props = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            if (input == null)
            {
                throw new RuntimeException(CONFIG_FILE + " not found on classpath.");
            }
            props.load(input);
        }
        catch (Exception e)
        {
            throw new RuntimeException("Failed to load " + CONFIG_FILE, e);
        }
        return props;
    }
    //open the connection for the database.
    public Connection connect()
    {
        try
        {
            if (connection == null || connection.isClosed())
            {
                connection = DriverManager.getConnection(url, username, password);
            }
        }
        catch (SQLException e)
        {
            throw new RuntimeException("Failed to connect to the database", e);
        }
        return connection;
    }
    //Closes the connection if it remain opens.
    public void disconnect()
    {
        try
        {
            if (connection != null && !connection.isClosed())
            {
                connection.close();
            }
        }
        catch (SQLException e)
        {
            System.err.println("Error closing connection: " + e.getMessage());
        }
    }
    //returns the current connection.
    public Connection getConnection()
    {
        return connect();
    }
}