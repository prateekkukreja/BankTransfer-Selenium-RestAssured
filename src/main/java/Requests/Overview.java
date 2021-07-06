package Requests;

import ConfigManagers.LocalConfigs;
import io.restassured.builder.RequestSpecBuilder;

public class Overview extends BaseApi {

    public Overview() {
        setMethod(MethodType.GET);

        RequestSpecBuilder request = getRequestSpecBuilder();

        request.setBaseUri(LocalConfigs.URL);
        request.setBasePath("overview.htm");

        request.addHeader("cookie", "JSESSIONID=" + helperActions.getTokenFromFile());

//        request.addCookie(helperActions.getTokenFromFile());
    }

}
