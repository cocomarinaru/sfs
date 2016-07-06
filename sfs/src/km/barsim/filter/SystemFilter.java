package km.barsim.filter;

import com.smartfoxserver.v2.controllers.filter.SysControllerFilter;
import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.variables.SFSUserVariable;
import com.smartfoxserver.v2.entities.variables.UserVariable;
import com.smartfoxserver.v2.exceptions.SFSException;
import com.smartfoxserver.v2.extensions.ExtensionLogLevel;
import com.smartfoxserver.v2.extensions.filter.FilterAction;
import km.barsim.db.UserService;

/**
 * Created by ovidiu on 06.07.2016.
 */
public class SystemFilter extends SysControllerFilter {


    @Override
    public FilterAction handleClientRequest(User user, ISFSObject isfsObject) throws SFSException {

        UserService userService = UserService.getInstance();

        trace(ExtensionLogLevel.INFO, "User login/join room: ", user.getName());

        int userRank = userService.getUserRank(user.getName());
        trace(ExtensionLogLevel.INFO, "User rank is: " + userRank);

        user.setProperty("ranking", userRank);

        UserVariable variable = new SFSUserVariable("rank", userRank);
        user.setVariable(variable);

        return FilterAction.CONTINUE;
    }
}
