package Scenarios;

import Commons.AccountActions;
import Commons.LoginActions;
import Core.BaseClass;
import org.testng.annotations.Test;

// Scenario #1 && 2
public class CreateAndVerifyAccountsTest extends BaseClass {

    LoginActions login = new LoginActions();
    AccountActions accts = new AccountActions();

    @Test
    public void createAndVerifyNewAccountTest() {
        login.loginWithValidUser(getDriver());
        accts.createCheckingAccount(getDriver());
        accts.createSavingsAccount(getDriver());
    }

}