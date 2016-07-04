package km.barsim.db;

import km.barsim.util.BarSimConstants;
import km.barsim.util.GameApiHelper;

/**
 * Created by bruno on 30/06/16.
 */
public class DBConfig {

    private final String dbUrl;
    private final String username;
    private final String password;
    private final String driver;

    private static DBConfig instance;

    public static DBConfig getInstance() {

        if (instance == null) {
            instance = new DBConfig();
        }

        return instance;
    }


    private DBConfig() {

        com.smartfoxserver.v2.db.DBConfig config = GameApiHelper.getInstance().getZoneDbConfig(BarSimConstants.ZONE_NAME);

        dbUrl = config.connectionString;
        username = config.userName;
        password = config.password;
        driver = config.driverName;

    }

    public String getDbUrl() {
        return dbUrl;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getDriver() {
        return driver;
    }
}
