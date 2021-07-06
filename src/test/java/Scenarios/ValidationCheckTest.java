package Scenarios;

import Commons.LoginActions;
import Core.BaseClass;
import org.testng.annotations.Test;

public class ValidationCheckTest extends BaseClass {

    final LoginActions login = new LoginActions();

    @Test
    public void LoginTestValidCredsTest() {
        login.loginWithValidUser(getDriver());
    }

    @Test
    public void LoginTestInValidCredsTest() {
        login.loginWithInValidUser(getDriver());
    }
}
