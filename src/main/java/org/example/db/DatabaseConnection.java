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
        String host = props.getProperty("db.host", "localhost");
        String port = props.getProperty("db.port", "3306");
        String name = props.getProperty("db.name", "world");
        this.url = "jdbc:mysql://" + host + ":" + port + "/" + name + "?useSSL=false&serverTimezone=UTC";
        this.username = props.getProperty("db.username", "root");
        this.password = props.getProperty("db.password", "");
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