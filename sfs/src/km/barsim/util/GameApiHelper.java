package km.barsim.util;

import com.smartfoxserver.v2.SmartFoxServer;
import com.smartfoxserver.v2.api.ISFSGameApi;
import com.smartfoxserver.v2.db.DBConfig;
import com.smartfoxserver.v2.entities.Room;
import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.Zone;
import com.smartfoxserver.v2.extensions.ExtensionLogLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class GameApiHelper {
    private SmartFoxServer serverInstance = SmartFoxServer.getInstance();
    protected final Logger logger = LoggerFactory.getLogger("Extensions");
    private static GameApiHelper instance = null;

    private GameApiHelper() {
    }

    public static GameApiHelper getInstance() {
        if (instance == null) {
            instance = new GameApiHelper();
        }
        return instance;
    }

    public User getUserByUserName(String username) {
        return serverInstance.getUserManager().getUserByName(username);
    }

    public Zone getZoneByName(String zoneName) {
        return serverInstance.getZoneManager().getZoneByName(zoneName);
    }

    public ISFSGameApi getGameApi() {
        return serverInstance.getAPIManager().getGameApi();
    }

    public Room getGameRoomOfUser(User user) {

        Zone barsimZone = getZoneByName(BarSimConstants.ZONE_NAME);
        List<Room> roomList = barsimZone.getRoomList();

        for (Room room : roomList) {
            if (room.containsUser(user) && room.isGame()) {
                return room;
            }
        }
        return null;
    }

    public SmartFoxServer getServerInstance() {
        return serverInstance;
    }


    public DBConfig getZoneDbConfig(String zoneName){
        return serverInstance.getConfigurator().getZoneSetting(zoneName).databaseManager;
    }


    public void disconnectGhostUser(String username) {

        logger.info("Trying to remove " + String.valueOf(username) + "(ghost User)");

        SmartFoxServer server = GameApiHelper.getInstance().getServerInstance();

        Zone zone = server.getZoneManager().getZoneByName(BarSimConstants.ZONE_NAME);

        for (Room room : zone.getRoomList()) {

            logger.info("Room " + room.getName() + " -> users : " + room.getUserList().size());

            User user = room.getUserByName(username);
            if (user != null) {
                logger.info("Found and removing " + username + " from room " + room.getName());
                room.removeUser(user);
            }

            for (User userInRoom : room.getUserList()) {
                logger.info("User: " + userInRoom.getName());
                if (userInRoom.getName().equals(username)) {
                    logger.info("!Found and removing " + username + " from room " + room.getName());
                    room.removeUser(userInRoom);
                }
            }
        }
    }
}

