package km.barsim.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

/**
 * Created by ovidiu on 23.06.2016.
 */
public class UserService {

    protected final Logger logger = LoggerFactory.getLogger("Extensions");

    private static UserService instance;

    public static UserService getInstance() {

        if (instance == null) {
            instance = new UserService();
        }

        return instance;
    }

    private UserService() {
    }

    public int getUserId(String userName) {

        int defaultId = 0;

        Connection connection = DatabaseConnector.getInstance().getConnection();

        try {

            String sql = "SELECT id FROM user WHERE username = ? LIMIT 1";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, userName);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }


        } catch (SQLException e) {
            logger.error("Error getting id for user " + userName, e);
        }
        finally {
            try {
                if(connection!=null) {
                    connection.close();
                }
            } catch (SQLException e) {
                logger.error("Error closing connection", e);
            }
        }
        return defaultId;
    }


    public int getUserRank(String userName) {

        int defaultRank = 0;
        Connection connection = DatabaseConnector.getInstance().getConnection();

        try {

            int userId = getUserId(userName);
            String sql = "SELECT x.user_id, x.position, x.h2h_score " +
                    " FROM (SELECT t.user_id, t.h2h_score, @rownum := @rownum + 1 AS position " +
                    " FROM user_score_summary t " +
                    " JOIN (SELECT @rownum := 0) r " +
                    " ORDER BY t.h2h_score DESC) x " +
                    " WHERE x.user_id = ?";

            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, userId);


            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("position");
            }


        } catch (SQLException e) {
            logger.error("Error getting rank for user" + userName, e);
        } finally {

            if(connection != null){
                try {
                    connection.close();
                } catch (SQLException e) {
                    logger.error("Error closing connection", e);
                }
            }

        }
        return defaultRank;
    }

}
