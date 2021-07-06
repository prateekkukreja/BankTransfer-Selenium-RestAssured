package Requests;

import ConfigManagers.LocalConfigs;
import Enums.CONSTANTCODES;
import io.restassured.builder.RequestSpecBuilder;

public class OpenNewAccounts extends BaseApi {

    public OpenNewAccounts(String accountType) {
        setMethod(MethodType.POST);

        RequestSpecBuilder request = getRequestSpecBuilder();

        request.setBaseUri(LocalConfigs.URL);
        request.setBasePath("services_proxy/bank/createAccount");

//        HashMap<String, Integer> map = new HashMap<>();
        int acctType;
        request.addQueryParam("customerId", LocalConfigs.CUST_ID);
        if (accountType.equalsIgnoreCase(CONSTANTCODES.CHECKING)) {
            acctType = 0;
        } else if (accountType.equalsIgnoreCase(CONSTANTCODES.SAVINGS)) {
            acctType = 1;
        } else {
            acctType = -1;
        }

        request.addQueryParam("newAccountType", acctType);
        request.addQueryParam("fromAccountId", Integer.parseInt(helperActions.getBaseAcctFromFile()));

        request.addHeader("cookie", "JSESSIONID=" + helperActions.getTokenFromFile());

    }

}
