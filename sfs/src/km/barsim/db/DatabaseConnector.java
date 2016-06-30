package km.barsim.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by ovidiu on 23.06.2016.
 */
public class DatabaseConnector {

    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";

    private DBConfig config = DBConfig.getInstance();


    public Connection getConnection() {
        Connection conn;

        try {
            Class.forName(JDBC_DRIVER);

            conn = DriverManager.getConnection(config.getDbUrl(), config.getUsername(), config.getPassword());

        } catch (ClassNotFoundException e) {
            return null;
        } catch (SQLException e) {
            return null;
        }
        return conn;
    }

    public void closeConnection() {

        try {
            connection.close();
        } catch (SQLException e) {

        }
    }
}