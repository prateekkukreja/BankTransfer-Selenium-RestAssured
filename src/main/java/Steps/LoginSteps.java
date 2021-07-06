package Steps;

import ConfigManagers.LocalConfigs;
import Core.BaseClass;
import Core.HelperActions;
import POJO.User;
import Requests.Login;
import Requests.Overview;
import Utils.ClientUtil;
import io.restassured.response.Response;

import java.io.IOException;

public class LoginSteps extends BaseClass {

    final Utils.ClientUtil ClientUtil = new ClientUtil();
    final HelperActions helperActions = new HelperActions();

    //contructor for positive tests
    public void loginSteps() throws IOException, InterruptedException {

        User user = new User();
        user.setUsername(LocalConfigs.USER);
        user.setPassword(LocalConfigs.PASS);

        Login login = new Login(user);
        Response response = login.execute();
        ClientUtil.checkStatusIs302(response);

        if (!(response.getHeader("set-cookie") == null)) {
            String[] newToken = response.getHeader("set-cookie").split("=|\\;");
            helperActions.updateInFile("JSESSIONID:" + newToken[1]);
        }

        Overview overview = new Overview();
        response = overview.execute();
        ClientUtil.checkStatusIs200(response);
    }

    //contructor for negative tests
    public void loginSteps(String type) throws IOException, InterruptedException {

        User user = new User();
        if (type.equalsIgnoreCase("negative")) {
            user.setUsername("abc");
        } else {
            user.setUsername(LocalConfigs.USER);
        }
        user.setPassword(LocalConfigs.PASS);

        Login login;
        if (type.equalsIgnoreCase("negative") || type.equalsIgnoreCase("invalid")) {
            login = new Login("testUrl", user);
        } else {
            login = new Login(user);
        }
        Response response = login.execute();
        if (type.equalsIgnoreCase("negative") || type.equalsIgnoreCase("invalid")) {
            ClientUtil.checkErrorStatusCode(response);
        } else {
            ClientUtil.checkStatusIs302(response);
        }

        if (!(response.getHeader("set-cookie") == null)) {
            String[] newToken = response.getHeader("set-cookie").split("=|\\;");
            helperActions.updateInFile("JSESSIONID:" + newToken[1]);
        }

        Overview overview = new Overview();
        response = overview.execute();
        if (type.equalsIgnoreCase("negative") || type.equalsIgnoreCase("invalid")) {
            ClientUtil.checkErrorStatusCode(response);
        } else {
            ClientUtil.checkStatusIs200(response);
        }
    }
}
