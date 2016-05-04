package km.barsim.handler;

import com.smartfoxserver.v2.entities.Room;
import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.extensions.ExtensionLogLevel;
import km.barsim.util.BarSimConstants;
import km.barsim.util.GameApiHelper;

import java.util.List;

public class GameContinueClientRequest extends AbstractSFSClientRequestHandler {

    public void handleClientRequest(User user, ISFSObject isfsObject) {

        user.setProperty(BarSimConstants.GAME_CONTINUE_PROPERTY, Boolean.TRUE);
        GameApiHelper helper = GameApiHelper.getInstance();
        Room gameRoom = helper.getGameRoomOfUser(user);

        if (gameRoom != null) {
            User opponent = getOpponent(user, gameRoom);
            if (opponent != null) {
                Boolean gameContinue = (Boolean) opponent.getProperty(BarSimConstants.GAME_CONTINUE_PROPERTY);
                if (gameContinue) {
                    List<Integer> recipes = getRecipes();
                    sendStartGame(user, opponent, recipes);
                    sendStartGame(opponent, user, recipes);
                }
            } else {
                trace(ExtensionLogLevel.INFO, "Opponent is null");
            }
        } else {
            trace(ExtensionLogLevel.INFO, "Game room is null");
        }
    }

    private User getOpponent(User user, Room gameRoom) {
        for (User userInRoom : gameRoom.getUserList()) {

            if (!userInRoom.equals(user)) {
                return userInRoom;
            }
        }

        return null;
    }


}

