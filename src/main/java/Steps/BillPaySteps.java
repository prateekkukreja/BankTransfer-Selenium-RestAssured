package Steps;

import Core.BaseClass;
import Core.HelperActions;
import Requests.BillPayPage;
import Requests.BillPayXfer;
import Utils.ClientUtil;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;

import java.io.IOException;

public class BillPaySteps extends BaseClass {

    final Utils.ClientUtil clientUtil = new ClientUtil();
    final HelperActions helperActions = new HelperActions();
    JsonPath jsonRsponse;

    public void billPaySteps() throws IOException, InterruptedException {

        LoginSteps steps = new LoginSteps();
        steps.loginSteps();

        BillPayPage billPay = new BillPayPage();
        Response response = billPay.execute();
        clientUtil.checkStatusIs200(response);
    }

    public void billPaySteps(String FromAcct, String ToAcct) throws IOException, InterruptedException {

        int fromAcctNumber = 0, toAcctNumber = 0;

        NewAccountSteps newAcctSteps = new NewAccountSteps();
        newAcctSteps.newAccountSteps(FromAcct);

        newAcctSteps = new NewAccountSteps();
        // already on Bill pay page so sending flag as True
        if (ToAcct.equalsIgnoreCase("demo") && FromAcct.equalsIgnoreCase("SAVINGS")){
            ToAcct = "CHECKING";
        }else if (ToAcct.equalsIgnoreCase("demo") && FromAcct.equalsIgnoreCase("CHECKING")){
            ToAcct = "SAVINGS";
        }
        newAcctSteps.newAccountSteps(true, ToAcct);

        if (FromAcct.equalsIgnoreCase("CHECKING")) {
            fromAcctNumber = Integer.parseInt(helperActions.getKeyFromFile("CHECKING"));
            toAcctNumber = Integer.parseInt(helperActions.getKeyFromFile("SAVINGS"));
        } else if (FromAcct.equalsIgnoreCase("SAVINGS")) {
            fromAcctNumber = Integer.parseInt(helperActions.getKeyFromFile("SAVINGS"));
            toAcctNumber = Integer.parseInt(helperActions.getKeyFromFile("CHECKING"));
        } else if (FromAcct.equalsIgnoreCase("demo") && ToAcct.equalsIgnoreCase("SAVINGS")){
            fromAcctNumber = 10019977;
            toAcctNumber = Integer.parseInt(helperActions.getKeyFromFile("SAVINGS"));
        }    else if (FromAcct.equalsIgnoreCase("demo") && ToAcct.equalsIgnoreCase("CHECKING")) {
            fromAcctNumber = 54321678;
            toAcctNumber = Integer.parseInt(helperActions.getKeyFromFile("CHECKING"));
        } else if (FromAcct.equalsIgnoreCase("SAVINGS") && ToAcct.equalsIgnoreCase("demo")){
            fromAcctNumber = Integer.parseInt(helperActions.getKeyFromFile("SAVINGS"));
            toAcctNumber = 123456789;
        }    else if (FromAcct.equalsIgnoreCase("CHECKING") && ToAcct.equalsIgnoreCase("demo")) {
            fromAcctNumber = Integer.parseInt(helperActions.getKeyFromFile("CHECKING"));
            toAcctNumber = 98765431;
        }

        Thread.sleep(2000);
        BillPayXfer openNewAcct = new BillPayXfer(fromAcctNumber, toAcctNumber);
        Response response = openNewAcct.execute();

        if (FromAcct.equalsIgnoreCase("demo") || ToAcct.equalsIgnoreCase("demo")) {
            clientUtil.checkErrorStatusCode(response);
        } else {
            clientUtil.checkStatusIs200(response);
            jsonRsponse = response.jsonPath();

            Assert.assertEquals(Integer.parseInt(jsonRsponse.get("amount").toString()), 200);
        }
    }
}