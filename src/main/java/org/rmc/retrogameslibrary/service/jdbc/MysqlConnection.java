package org.rmc.retrogameslibrary.service.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// Singleton class
public class MysqlConnection {

    private static MysqlConnection instance = null;

    private Connection connection;

    private MysqlConnection() {
        connection = null;
    }

    // Return a MysqlConnection instance
    public static MysqlConnection getInstance() {
        if (instance == null)
            instance = new MysqlConnection();
        return instance;
    }

    // Set MySQL connection
    public void connect(String host, String user, String pass, String db)
            throws SQLException {
        connection = DriverManager.getConnection(
                "jdbc:mysql://" + host + "/" + db + "?serverTimezone=UTC", user, pass);
    }

    // Return the Connection
    public Connection getConnection() {
        return connection;
    }

    // Close resources
    public void closeConnection() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }
}
