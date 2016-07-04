package km.barsim.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by ovidiu on 23.06.2016.
 */
public class DatabaseConnector {

    protected final Logger logger = LoggerFactory.getLogger("Extensions");

    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";

    private DBConfig config;

    private static DatabaseConnector instance;

    private DatabaseConnector() {
        config = DBConfig.getInstance();

        logger.info("Database connector");
        logger.info("config driver" + config.getDriver());
        logger.info("config  dbUrl" + config.getDbUrl());
        logger.info("config  user," + config.getUsername());
    }

    public static DatabaseConnector getInstance() {

        if (instance == null) {
            instance = new DatabaseConnector();
        }

        return instance;
    }


    public Connection getConnection() {
        Connection conn;

        try {
            Class.forName(config.getDriver());

            conn = DriverManager.getConnection(config.getDbUrl(), config.getUsername(), config.getPassword());

        } catch (ClassNotFoundException e) {
            logger.error("Error finding driver for db ", e);
            return null;
        } catch (SQLException e) {
            logger.error("Error with connection", e);
            return null;
        }
        return conn;
    }


}