/*
 * Decompiled with CFR 0_114.
 * 
 * Could not load the following classes:
 *  com.smartfoxserver.v2.SmartFoxServer
 *  com.smartfoxserver.v2.api.APIManager
 *  com.smartfoxserver.v2.api.CreateRoomSettings
 *  com.smartfoxserver.v2.api.ISFSApi
 *  com.smartfoxserver.v2.api.ISFSGameApi
 *  com.smartfoxserver.v2.entities.Room
 *  com.smartfoxserver.v2.entities.User
 *  com.smartfoxserver.v2.entities.Zone
 *  com.smartfoxserver.v2.entities.data.ISFSObject
 *  com.smartfoxserver.v2.entities.data.SFSObject
 *  com.smartfoxserver.v2.entities.invitation.Invitation
 *  com.smartfoxserver.v2.entities.invitation.InvitationCallback
 *  com.smartfoxserver.v2.entities.invitation.SFSInvitation
 *  com.smartfoxserver.v2.entities.variables.RoomVariable
 *  com.smartfoxserver.v2.entities.variables.SFSRoomVariable
 *  com.smartfoxserver.v2.exceptions.SFSCreateRoomException
 *  com.smartfoxserver.v2.exceptions.SFSJoinRoomException
 *  com.smartfoxserver.v2.exceptions.SFSVariableException
 *  com.smartfoxserver.v2.extensions.BaseClientRequestHandler
 *  com.smartfoxserver.v2.extensions.ExtensionLogLevel
 */
package km.barsim.handler;

import com.smartfoxserver.v2.SmartFoxServer;
import com.smartfoxserver.v2.api.APIManager;
import com.smartfoxserver.v2.api.CreateRoomSettings;
import com.smartfoxserver.v2.api.ISFSApi;
import com.smartfoxserver.v2.api.ISFSGameApi;
import com.smartfoxserver.v2.entities.Room;
import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.Zone;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.data.SFSObject;
import com.smartfoxserver.v2.entities.invitation.Invitation;
import com.smartfoxserver.v2.entities.invitation.InvitationCallback;
import com.smartfoxserver.v2.entities.invitation.SFSInvitation;
import com.smartfoxserver.v2.entities.variables.RoomVariable;
import com.smartfoxserver.v2.entities.variables.SFSRoomVariable;
import com.smartfoxserver.v2.exceptions.SFSCreateRoomException;
import com.smartfoxserver.v2.exceptions.SFSJoinRoomException;
import com.smartfoxserver.v2.exceptions.SFSVariableException;
import com.smartfoxserver.v2.extensions.BaseClientRequestHandler;
import com.smartfoxserver.v2.extensions.ExtensionLogLevel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import km.barsim.util.BarSimConstants;
import km.barsim.util.GameApiHelper;

public class InvitationClientRequestHandler extends AbstractSFSClientRequestHandler {

    private GameApiHelper helper = GameApiHelper.getInstance();

    public void handleClientRequest(User sender, ISFSObject params) {

        String username = params.getUtfString(BarSimConstants.INVITATION_PARAMS_USERNAME);
        trace(ExtensionLogLevel.INFO, String.valueOf(sender.getName()) + " has sent an invitation message for user: " + username);

        if (username != null && !username.isEmpty()) {

            User receiver = helper.getUserByUserName(username);

            if (receiver == null) {
                trace(ExtensionLogLevel.INFO, String.valueOf(username) + " has not been found !");
            } else {
                trace(ExtensionLogLevel.INFO, String.valueOf(username) + " has been found !");
                sendInvitation(sender, receiver);
            }
        }
    }

    private void sendInvitation(User sender, User receiver) {

        trace(ExtensionLogLevel.INFO, "Sending invitation from " + sender.getName() + "  to " + receiver.getName());
        SFSInvitation invitation = new SFSInvitation(sender, receiver, 50);

        helper.getGameApi().sendInvitation(invitation, new InvitationCallback() {

            public void onRefused(Invitation invitation, ISFSObject params) {
                trace(ExtensionLogLevel.INFO, "User refused invitation " + invitation.getInvitee().getName());
                SFSObject reply = new SFSObject();
                reply.putUtfString(BarSimConstants.INVITATION_PARAMS_USERNAME, invitation.getInvitee().getName());
                send(BarSimConstants.INVITATION_REFUSED, reply, invitation.getInviter());
            }

            public void onExpired(Invitation invitation) {
                trace(ExtensionLogLevel.INFO, "Invitation expired !");
                SFSObject reply = new SFSObject();
                reply.putUtfString(BarSimConstants.INVITATION_PARAMS_USERNAME, invitation.getInvitee().getName());
                send(BarSimConstants.INVITATION_EXPIRED, reply, invitation.getInviter());
            }

            public void onAccepted(Invitation invitation, ISFSObject params) {
                trace(ExtensionLogLevel.INFO, "Invitation accepted  !");
                onAcceptedInvitation(invitation);
            }
        });
    }

    private void onAcceptedInvitation(Invitation invitation) {
        User player2;
        User player1 = invitation.getInviter();

        if (createPrivateRoom(player1, player2 = invitation.getInvitee())) {
            sendStartGame(player1, player2);
            sendStartGame(player2, player1);
            trace(ExtensionLogLevel.INFO, "Rooms in Barsim :");
            Zone barsimZone = helper.getZoneByName(BarSimConstants.ZONE_NAME);
            for (Room room : barsimZone.getRoomList()) {
                trace(ExtensionLogLevel.INFO, "Room :" + room.getName());
                for (User user : room.getUserList()) {
                    trace(ExtensionLogLevel.INFO, "User: " + user.getName());
                }
            }
        } else {
            trace(ExtensionLogLevel.INFO, "Failed to create Game room for " + player1 + "and " + player2);
        }
    }

    private boolean createPrivateRoom(User player1, User player2) {

        ISFSApi sfsApi = SmartFoxServer.getInstance().getAPIManager().getSFSApi();
        Zone barsimZone = helper.getZoneByName(BarSimConstants.ZONE_NAME);

        int gameRoomCount = barsimZone.getGameRoomCount();
        CreateRoomSettings roomSettings = new CreateRoomSettings();
        roomSettings.setGame(true);
        roomSettings.setName("PG_" + gameRoomCount);
        roomSettings.setMaxUsers(2);

        try {
            Room gameRoom = sfsApi.createRoom(barsimZone, roomSettings, player1);
            try {
                gameRoom.setVariable(new SFSRoomVariable("originalRoom", player1.getLastJoinedRoom().getName()));
            } catch (SFSVariableException e) {
                trace(ExtensionLogLevel.ERROR, "Error setting original room: " + e.getMessage());
            }

            sfsApi.leaveRoom(player1, player1.getLastJoinedRoom());
            sfsApi.leaveRoom(player2, player2.getLastJoinedRoom());
            sfsApi.joinRoom(player1, gameRoom);
            sfsApi.joinRoom(player2, gameRoom);

        } catch (SFSCreateRoomException e) {
            trace(ExtensionLogLevel.ERROR, "Error creating game room: " + e.getMessage());
            return false;
        } catch (SFSJoinRoomException e) {
            trace(ExtensionLogLevel.ERROR, "Error joining game room: " + e.getMessage());
            return false;
        }
        return true;
    }


}

