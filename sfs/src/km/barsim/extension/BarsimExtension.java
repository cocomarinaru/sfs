package km.barsim.extension;

import com.smartfoxserver.v2.controllers.SystemRequest;
import com.smartfoxserver.v2.controllers.filter.ISystemFilterChain;
import com.smartfoxserver.v2.controllers.filter.SysControllerFilterChain;
import com.smartfoxserver.v2.core.SFSEventType;
import com.smartfoxserver.v2.extensions.ExtensionLogLevel;
import com.smartfoxserver.v2.extensions.SFSExtension;
import km.barsim.db.DatabaseConnector;
import km.barsim.filter.SystemFilter;
import km.barsim.handler.GameContinueClientRequest;
import km.barsim.handler.GameEndClientRequest;
import km.barsim.handler.InvitationClientRequestHandler;
import km.barsim.handler.UserRankHandler;
import km.barsim.util.BarSimConstants;

public class BarsimExtension extends SFSExtension {

    public void init() {
        trace(ExtensionLogLevel.INFO, "Bar Simulator SFS2X Extension!");

        DatabaseConnector.getInstance();

        addRequestHandler(BarSimConstants.INVITATION_COMMAND, InvitationClientRequestHandler.class);
        addRequestHandler(BarSimConstants.GAME_END_COMMAND, GameEndClientRequest.class);
        addRequestHandler(BarSimConstants.GAME_CONTINUE_COMMAND, GameContinueClientRequest.class);

//        addEventHandler(SFSEventType.USER_JOIN_ROOM, UserRankHandler.class);
//        addEventHandler(SFSEventType.USER_LOGIN, UserRankHandler.class);

        getParentZone().resetSystemFilterChain();
        ISystemFilterChain filterChain = new SysControllerFilterChain();
        filterChain.addFilter("joinRoomFilter", new SystemFilter());
        getParentZone().setFilterChain(SystemRequest.JoinRoom, filterChain);
//
    }
}

