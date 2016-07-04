//package km.barsim.filter;
//
//import com.smartfoxserver.v2.controllers.filter.SysControllerFilter;
//import com.smartfoxserver.v2.entities.User;
//import com.smartfoxserver.v2.entities.data.ISFSObject;
//import com.smartfoxserver.v2.exceptions.SFSException;
//import com.smartfoxserver.v2.extensions.ExtensionLogLevel;
//import com.smartfoxserver.v2.extensions.filter.FilterAction;
//
///**
// * Created by bruno on 05/07/16.
// */
//public class SystemFilter extends SysControllerFilter {
//
//    public String message;
//
//    public SystemFilter(String message) {
//        this.message = message;
//    }
//
//    @Override
//    public FilterAction handleClientRequest(User user, ISFSObject object) throws SFSException {
//
//        trace(ExtensionLogLevel.INFO, "System Filter: " + message);
//
//        String userName = user.getName();
//        trace(ExtensionLogLevel.INFO, "User: " + userName);
//
//        trace(ExtensionLogLevel.INFO, "Object dump: " + object.getDump());
//
//
//        return FilterAction.CONTINUE;
//    }
//}
