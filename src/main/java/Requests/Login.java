package Requests;

import ConfigManagers.LocalConfigs;
import POJO.User;
import io.restassured.builder.RequestSpecBuilder;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Arrays;

public class Login extends BaseApi {

    public Login(User user) throws IOException {
        setMethod(MethodType.POST);

        RequestSpecBuilder request = getRequestSpecBuilder();

        request.setBaseUri(LocalConfigs.URL);
        request.setBasePath("login.htm");

        request.addHeader("cookie", "JSESSION=" + helperActions.getTokenFromFile());
        request.addHeader("content-type", "application/x-www-form-urlencoded");
        request.addHeader("referer", "https://parabank.parasoft.com/parabank/index.htm?ConnType=JDBC");

        request.addCookie(helperActions.getTokenFromFile());

        request.setBody(EntityUtils.toString(new UrlEncodedFormEntity(Arrays.asList(
                new BasicNameValuePair("username", user.getUsername()),
                new BasicNameValuePair("password", user.getPassword())
        ))));
    }

    public Login(String invalid, User user) throws IOException {
        setMethod(MethodType.POST);

        RequestSpecBuilder request = getRequestSpecBuilder();

        request.setBaseUri(LocalConfigs.URL);
        request.setBasePath(invalid);

        request.addHeader("cookie", "JSESSION=" + helperActions.getTokenFromFile());
        request.addHeader("content-type", "application/x-www-form-urlencoded");
        request.addHeader("referer", "https://parabank.parasoft.com/parabank/index.htm?ConnType=JDBC");

        request.addCookie(helperActions.getTokenFromFile());

        request.setBody(EntityUtils.toString(new UrlEncodedFormEntity(Arrays.asList(
                new BasicNameValuePair("username", user.getUsername()),
                new BasicNameValuePair("password", user.getPassword())
        ))));

    }

}
