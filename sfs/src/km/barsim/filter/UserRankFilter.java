//package km.barsim.filter;
//
//import com.smartfoxserver.v2.controllers.filter.SysControllerFilter;
//import com.smartfoxserver.v2.core.ISFSEvent;
//import com.smartfoxserver.v2.entities.User;
//import com.smartfoxserver.v2.entities.data.ISFSObject;
//import com.smartfoxserver.v2.exceptions.SFSException;
//import com.smartfoxserver.v2.extensions.ExtensionLogLevel;
//import com.smartfoxserver.v2.extensions.SFSExtension;
//import com.smartfoxserver.v2.extensions.filter.FilterAction;
//import com.smartfoxserver.v2.extensions.filter.SFSExtensionFilter;
//import km.barsim.db.UserService;
//
///**
// * Created by bruno on 04/07/16.
// */
//public class UserRankFilter extends SFSExtensionFilter {
//
//    private UserService userService;
//
//    @Override
//    public void init(SFSExtension ext) {
//        super.init(ext);
//        trace(ExtensionLogLevel.INFO, "Filter inited!");
//    }
//
//    public UserRankFilter() {
//
//        userService = UserService.getInstance();
//    }
//
//    @Override
//    public void destroy() {
//
//        trace(ExtensionLogLevel.INFO, "destroy");
//    }
//
//    @Override
//    public FilterAction handleClientRequest(String s, User user, ISFSObject object) throws SFSException {
//
//        trace(ExtensionLogLevel.INFO, "handleClientRequest");
//        trace(ExtensionLogLevel.INFO, "Command: " + s);
//
//        String userName = user.getName();
//        trace(ExtensionLogLevel.INFO, "User joined room " + userName);
//
//        int userRank = userService.getUserRank(userName);
//        trace(ExtensionLogLevel.INFO, "User rank is: " + userRank);
//
//        trace(ExtensionLogLevel.INFO, "Object dump: " + object.getDump());
//
//        return FilterAction.CONTINUE;
//    }
//
//    @Override
//    public FilterAction handleServerEvent(ISFSEvent event) throws SFSException {
//
//        trace(ExtensionLogLevel.INFO, "event type: " + event.getType());
//
//        return FilterAction.CONTINUE;
//    }
////
////    @Override
////    public FilterAction handleClientRequest(User user, ISFSObject object) throws SFSException {
////
////        String userName = user.getName();
////        trace(ExtensionLogLevel.INFO, "User joined room " + userName);
////
////        int userRank = userService.getUserRank(userName);
////
////        trace(ExtensionLogLevel.INFO, "User rank is: " + userRank);
////
////        object.putInt("rank", userRank);
////
////        trace(ExtensionLogLevel.INFO, "Object dump: " + object.getDump());
////
////
////        return FilterAction.CONTINUE;
////    }
//}
