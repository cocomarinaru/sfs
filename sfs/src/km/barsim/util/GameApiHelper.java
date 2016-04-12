package km.barsim.util;

import com.smartfoxserver.v2.SmartFoxServer;
import com.smartfoxserver.v2.api.ISFSGameApi;
import com.smartfoxserver.v2.entities.Room;
import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.Zone;

import java.util.List;

public class GameApiHelper {
    private SmartFoxServer serverInstance = SmartFoxServer.getInstance();
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


}

