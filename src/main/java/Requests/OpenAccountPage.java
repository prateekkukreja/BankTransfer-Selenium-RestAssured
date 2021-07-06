package Requests;

import ConfigManagers.LocalConfigs;
import io.restassured.builder.RequestSpecBuilder;

public class OpenAccountPage extends BaseApi {

    public OpenAccountPage() {
        setMethod(MethodType.GET);

        RequestSpecBuilder request = getRequestSpecBuilder();

        request.setBaseUri(LocalConfigs.URL);
        request.setBasePath("openaccount.htm");

        request.addHeader("cookie", "JSESSIONID=" + helperActions.getTokenFromFile());
    }
}
