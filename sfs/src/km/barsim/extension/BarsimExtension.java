package km.barsim.extension;

import com.smartfoxserver.v2.extensions.ExtensionLogLevel;
import com.smartfoxserver.v2.extensions.SFSExtension;
import km.barsim.handler.GameContinueClientRequest;
import km.barsim.handler.GameEndClientRequest;
import km.barsim.handler.InvitationClientRequestHandler;

import km.barsim.util.BarSimConstants;

public class BarsimExtension extends SFSExtension {

    public void init() {
        trace(ExtensionLogLevel.INFO, "Bar Simulator SFS2X Extension!");

        addRequestHandler(BarSimConstants.INVITATION_COMMAND, InvitationClientRequestHandler.class);
        addRequestHandler(BarSimConstants.GAME_END_COMMAND, GameEndClientRequest.class);
        addRequestHandler(BarSimConstants.GAME_CONTINUE_COMMAND, GameContinueClientRequest.class);
    }
}

