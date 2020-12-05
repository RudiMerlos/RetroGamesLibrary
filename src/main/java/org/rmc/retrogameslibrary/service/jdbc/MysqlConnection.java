package org.rmc.retrogameslibrary.service.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MysqlConnection {

    private static MysqlConnection instance = null;

    private Connection connection;

    private MysqlConnection() {
        connection = null;
    }

    public static MysqlConnection getInstance() {
        if (instance == null)
            instance = new MysqlConnection();
        return instance;
    }

    public void getConnection(String host, String user, String pass, String db)
            throws SQLException {
        connection = DriverManager.getConnection(
                "jdbc:mysql://" + host + "/" + db + "?serverTimezone=UTC", user, pass);
    }

    public void closeConnection() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }
}
