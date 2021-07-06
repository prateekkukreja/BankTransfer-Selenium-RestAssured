package Requests;

import ConfigManagers.LocalConfigs;
import io.restassured.builder.RequestSpecBuilder;

public class BillPayPage extends BaseApi {

    public BillPayPage() {
        setMethod(MethodType.GET);

        RequestSpecBuilder request = getRequestSpecBuilder();

        request.setBaseUri(LocalConfigs.URL);
        request.setBasePath("billpay.htm");

        request.addHeader("cookie", "JSESSIONID=" + helperActions.getTokenFromFile());
    }
}
