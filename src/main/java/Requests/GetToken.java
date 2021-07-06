package Requests;

import ConfigManagers.LocalConfigs;
import io.restassured.builder.RequestSpecBuilder;

public class GetToken extends BaseApi {

    public GetToken() {
        setMethod(MethodType.GET);

        RequestSpecBuilder request = getRequestSpecBuilder();

        request.addHeader("accept", "application/json");
        request.addHeader("Content-Type", "application/json");
        request.setBaseUri(LocalConfigs.URL);
        request.setBasePath("index.htm?ConnType=JDBC");
    }
}