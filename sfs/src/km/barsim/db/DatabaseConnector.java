package km.barsim.db;

import java.sql.*;

/**
 * Created by ovidiu on 23.06.2016.
 */
public class DatabaseConnector {

    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost/EMP";

    private static final String USER = "username";
    private static final String PASS = "password";

    private static Connection connection;

    public static Connection getConnection() {

        if (connection == null) {
            connection = connect();
        }

        return connection;
    }

    private static Connection connect() {
        Connection conn;

        try {
            Class.forName(JDBC_DRIVER);

            conn = DriverManager.getConnection(DB_URL, USER, PASS);

        } catch (ClassNotFoundException e) {
            return null;
        } catch (SQLException e) {
            return null;
        }
        return conn;
    }

    public static void closeConnection(){

        try {
            connection.close();
        } catch (SQLException e) {

        }
    }
}