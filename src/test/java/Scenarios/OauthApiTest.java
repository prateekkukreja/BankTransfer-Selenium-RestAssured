package Scenarios;

import Core.BaseClass;
import Steps.LoginSteps;
import org.testng.annotations.Test;

import java.io.IOException;

public class OauthApiTest extends BaseClass {

    @Test
    public void LoginPageTest() throws IOException, InterruptedException {

        LoginSteps steps = new LoginSteps();
        steps.loginSteps();
    }

    @Test
    public void LoginPageInvalidCredsTest() throws IOException, InterruptedException {

        LoginSteps steps = new LoginSteps();
        steps.loginSteps("negative");
    }

    @Test
    public void LoginPageInvalidURITest() throws IOException, InterruptedException {

        LoginSteps steps = new LoginSteps();
        steps.loginSteps("invalid");
    }
}

