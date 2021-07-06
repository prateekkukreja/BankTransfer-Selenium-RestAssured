package Requests;

import ConfigManagers.LocalConfigs;
import Core.HelperActions;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.Cookie;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.RequestSpecification;
import com.github.dzieciou.testing.curl.CurlLoggingRestAssuredConfigFactory;

import static io.restassured.RestAssured.given;

public class BaseApi {

    public MethodType method;
    final HelperActions helperActions = new HelperActions();
    public final RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();

    public enum MethodType {
        POST, GET, PUT, DELETE
    }

    public void setMethod(MethodType method) {
        this.method = method;
    }

    public RequestSpecBuilder getRequestSpecBuilder() {
        return requestSpecBuilder;
    }

    public Response execute() {

        FilterableRequestSpecification requestSpecification = (FilterableRequestSpecification) requestSpecBuilder.addFilter(new RequestLoggingFilter())
                .addFilter(new ResponseLoggingFilter()).build();
        requestSpecification.removeCookies();
        requestSpecification.removeHeader("accept");
        requestSpecification.removeHeader("Accept");

//            requestSpecification.auth().basic("prateek.kukreja", "test@123");
//            RestAssured.proxy("if..reqd", 8081);

        RestAssuredConfig config = CurlLoggingRestAssuredConfigFactory.createConfig();

        requestSpecification.auth().basic(LocalConfigs.USER, LocalConfigs.PASS);

        Response response;
        RestAssured.defaultParser = Parser.JSON;
        requestSpecification.urlEncodingEnabled(false);

//        RestAssuredConfig config = new http2curl().build();

        switch (method) {
            case GET:
                response = given().config(config).spec(requestSpecification).when().get();
                break;
            case POST:
                response = given().config(config).spec(requestSpecification).when().post();
                break;
            case PUT:
                response = given().spec(requestSpecification).when().put();
                break;
            case DELETE:
                response = given().spec(requestSpecification).when().delete();
                break;
            default:
                throw new RuntimeException("Api method not specified");
        }
        return response;
    }

    public Response executeGenerateToken() {
        RequestSpecification requestSpecification = requestSpecBuilder.addFilter(new RequestLoggingFilter())
                .addFilter(new ResponseLoggingFilter()).build();

        RestAssuredConfig config = CurlLoggingRestAssuredConfigFactory.createConfig();

        requestSpecification.auth().basic(LocalConfigs.USER, LocalConfigs.PASS);

        return given().config(config).cookie(generateToken()).spec(requestSpecification).when().get();
    }

    public Cookie generateToken() {
        Cookie JSESSIONID;
        RequestSpecification requestSpecification = requestSpecBuilder.addFilter(new RequestLoggingFilter())
                .addFilter(new ResponseLoggingFilter()).build();

        RestAssuredConfig config = CurlLoggingRestAssuredConfigFactory.createConfig();

        requestSpecification.auth().basic(LocalConfigs.USER, LocalConfigs.PASS);

        RestAssured.defaultParser = Parser.JSON;
        requestSpecification.urlEncodingEnabled(false);

        JSESSIONID = given().config(config).spec(requestSpecification).when().get().thenReturn().getDetailedCookie("JSESSIONID");
        helperActions.writeToFile("JSESSIONID:" + JSESSIONID.getValue());
        RestAssured.reset();
        return JSESSIONID;
    }

}
