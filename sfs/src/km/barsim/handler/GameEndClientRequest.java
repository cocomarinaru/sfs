
package km.barsim.handler;

import com.smartfoxserver.v2.SmartFoxServer;
import com.smartfoxserver.v2.entities.Room;
import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.Zone;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.exceptions.SFSJoinRoomException;
import com.smartfoxserver.v2.extensions.ExtensionLogLevel;
import km.barsim.util.BarSimConstants;

import java.util.List;

public class GameEndClientRequest extends AbstractSFSClientRequestHandler {

    public void handleClientRequest(User user, ISFSObject params) {
        Room gameRoom = helper.getGameRoomOfUser(user);
        if (gameRoom != null) {
            String originalRoomName = gameRoom.getVariable("originalRoom").getStringValue();
            Zone barSimZone = SmartFoxServer.getInstance().getZoneManager().getZoneByName(BarSimConstants.ZONE_NAME);
            Room originalRoom = barSimZone.getRoomByName(originalRoomName);
            if (originalRoom != null) {
                List<User> userList = gameRoom.getUserList();
                for (User userInRoom : userList) {
                    try {
                        SmartFoxServer.getInstance().getAPIManager().getSFSApi().joinRoom(userInRoom, originalRoom);
                    } catch (SFSJoinRoomException e) {
                        trace(ExtensionLogLevel.ERROR, "Error joining game room: " + e.getMessage());
                    }
                }
            } else {
                trace(ExtensionLogLevel.INFO, "Original room cannot be found: " + originalRoomName);
            }
            SmartFoxServer.getInstance().getAPIManager().getSFSApi().removeRoom(gameRoom);
        } else {
            trace(ExtensionLogLevel.INFO, "Game room cannot be found");
        }
    }

}

