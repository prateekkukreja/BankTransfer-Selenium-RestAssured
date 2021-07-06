package Requests;

import ConfigManagers.LocalConfigs;
import io.restassured.builder.RequestSpecBuilder;

public class GetAccountsByCustID extends BaseApi {

    public GetAccountsByCustID() {
        setMethod(MethodType.GET);

        RequestSpecBuilder request = getRequestSpecBuilder();

        request.setBaseUri(LocalConfigs.URL);
        request.setBasePath("services_proxy/bank/customers/" + LocalConfigs.CUST_ID + "/accounts");

        request.addHeader("cookie", "JSESSIONID=" + helperActions.getTokenFromFile());
    }
}
