package Scenarios;

import Core.BaseClass;
import Enums.CONSTANTCODES;
import Steps.NewAccountSteps;
import org.testng.annotations.Test;

import java.io.IOException;

public class OpenAccountsTest extends BaseClass {

    @Test
    public void openNewAcctPageTest() throws IOException, InterruptedException {
        NewAccountSteps steps = new NewAccountSteps();
        steps.newAccountSteps();
    }

    @Test
    public void OpenNewCheckingAccount() throws IOException, InterruptedException {

        NewAccountSteps steps = new NewAccountSteps();
        steps.newAccountSteps(CONSTANTCODES.CHECKING);
    }

    @Test
    public void OpenNewSavingsAccount() throws IOException, InterruptedException {

        NewAccountSteps steps = new NewAccountSteps();
        steps.newAccountSteps(CONSTANTCODES.SAVINGS);
    }


    @Test
    public void OpenNewOtherAccount() throws IOException, InterruptedException {

        NewAccountSteps steps = new NewAccountSteps();
        steps.newAccountSteps("demo");
    }

    @Test
    public void OpenInvalidAccountTypeAccount() throws IOException, InterruptedException {

        NewAccountSteps steps = new NewAccountSteps();
        steps.newAccountSteps("!@#$%^&*");
    }
}
