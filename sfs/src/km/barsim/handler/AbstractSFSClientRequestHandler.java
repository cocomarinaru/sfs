package km.barsim.handler;

import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.SFSObject;
import com.smartfoxserver.v2.extensions.BaseClientRequestHandler;
import km.barsim.util.BarSimConstants;
import km.barsim.util.GameApiHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public abstract class AbstractSFSClientRequestHandler extends BaseClientRequestHandler {

    private static final int MAX_ID = 115;
    private static final int MAX_RECIPES = 3;

    protected GameApiHelper helper  = GameApiHelper.getInstance();

    protected void sendStartGame(User user, User opponent, List<Integer> recipieList) {

        user.setProperty(BarSimConstants.GAME_CONTINUE_PROPERTY, Boolean.FALSE);
        opponent.setProperty(BarSimConstants.GAME_CONTINUE_PROPERTY, Boolean.FALSE);

        SFSObject sfsObjectWIthRecipesAndUserInfo = new SFSObject();
        sfsObjectWIthRecipesAndUserInfo.putIntArray(BarSimConstants.RECIPES, recipieList);
        sfsObjectWIthRecipesAndUserInfo.putUtfString("opponentName", opponent.getName());
        sfsObjectWIthRecipesAndUserInfo.putInt("opponentId", opponent.getId());

        send(BarSimConstants.INVITATION_START_GAME, sfsObjectWIthRecipesAndUserInfo, user);
    }

    protected List<Integer> getRecipes() {
        ArrayList<Integer> recipesList = new ArrayList<Integer>();

        Random random = new Random();

        for (int i = 0; i < MAX_RECIPES; i++) {
            int recipeId = random.nextInt(MAX_ID);
            recipesList.add(recipeId);
        }

        return recipesList;
    }
}
