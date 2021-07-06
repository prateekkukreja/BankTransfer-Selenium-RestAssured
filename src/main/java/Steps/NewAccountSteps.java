package Steps;

import Commons.Utility;
import Core.BaseClass;
import Core.HelperActions;
import Enums.CONSTANTCODES;
import Requests.GetAccountsByCustID;
import Requests.OpenAccountPage;
import Requests.OpenNewAccounts;
import Utils.ClientUtil;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

public class NewAccountSteps extends BaseClass {

    final Utils.ClientUtil clientUtil = new ClientUtil();
    final Utility utils = new Utility();
    final HelperActions helperActions = new HelperActions();
    JsonPath jsonResponse;

    public void newAccountSteps() throws IOException, InterruptedException {

        //Login
        LoginSteps steps = new LoginSteps();
        steps.loginSteps();

        //OpenAccounts overview page
        OpenAccountPage openAcct = new OpenAccountPage();
        Response response = openAcct.execute();
        clientUtil.checkStatusIs200(response);

        String html = response.asString();
        Assert.assertTrue(utils.AssertOpenNewAccount(html));
    }

    public void newAccountSteps(String AccountType) throws IOException, InterruptedException {

        LoginSteps steps = new LoginSteps();
        steps.loginSteps();

        OpenAccountPage openAcct = new OpenAccountPage();
        Response response = openAcct.execute();
        clientUtil.checkStatusIs200(response);
        String html = response.asString();
        Assert.assertTrue(utils.AssertOpenNewAccount(html));

        //getAccountsList
        GetAccountsByCustID getAcctsByCustID = new GetAccountsByCustID();
        response = getAcctsByCustID.execute();
        clientUtil.checkStatusIs200(response);

        jsonResponse = response.jsonPath();
        List<LinkedHashMap<String, Object>> list = jsonResponse.getJsonObject("$");
        CONSTANTCODES.BASEACCOUNT = Integer.parseInt(list.get(list.size() - 1).get("id").toString());

        helperActions.writeToFile("BaseAccount:" + CONSTANTCODES.BASEACCOUNT);

        //Open new Account
        OpenNewAccounts openNewAcct = new OpenNewAccounts(AccountType);
        response = openNewAcct.execute();

        if (AccountType.equalsIgnoreCase("SAVINGS") || AccountType.equalsIgnoreCase("CHECKING")) {
            clientUtil.checkStatusIs200(response);
            jsonResponse = response.jsonPath();

            Assert.assertEquals(AccountType, jsonResponse.get("type"));
            helperActions.writeToFile(jsonResponse.get("type") + ":" + jsonResponse.get("id"));
        } else {
            clientUtil.checkErrorStatusCode(response);
        }
    }

    public void newAccountSteps(boolean input, String AccountType) throws InterruptedException {
        //Open new Account
        OpenNewAccounts openNewAcct = new OpenNewAccounts(AccountType);
        Response response = openNewAcct.execute();
        clientUtil.checkStatusIs200(response);

        jsonResponse = response.jsonPath();

        Assert.assertEquals(AccountType, jsonResponse.get("type"));
        helperActions.writeToFile(jsonResponse.get("type") + ":" + jsonResponse.get("id"));
    }
}
