package km.barsim.handler;

import com.smartfoxserver.v2.core.ISFSEvent;
import com.smartfoxserver.v2.core.SFSEventParam;
import com.smartfoxserver.v2.entities.SFSUser;
import com.smartfoxserver.v2.exceptions.SFSException;
import com.smartfoxserver.v2.extensions.BaseServerEventHandler;
import com.smartfoxserver.v2.extensions.ExtensionLogLevel;
import km.barsim.db.UserService;

/**
 * Created by bruno on 05/07/16.
 */
public class UserRankHandler extends BaseServerEventHandler {


    public static final String RANKING = "ranking";
    private UserService userService = UserService.getInstance();

    @Override
    public void handleServerEvent(ISFSEvent event) throws SFSException {

        SFSUser user = (SFSUser) event.getParameter(SFSEventParam.USER);
        trace(ExtensionLogLevel.INFO, "User login/join room: ", user.getName());

        int userRank = userService.getUserRank(user.getName());
        trace(ExtensionLogLevel.INFO, "User rank is: " + userRank);

        user.setProperty(RANKING, userRank);
    }
}
