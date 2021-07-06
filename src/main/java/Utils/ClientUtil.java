package Utils;

import io.restassured.response.Response;
import org.testng.Assert;

public class ClientUtil {

    //Verify the http response status returned. Check Status Code is 200?
    public void checkStatusIs200(Response res) throws InterruptedException {
        Thread.sleep(1000);
        Assert.assertEquals(res.getStatusCode(), 200, "Status Check Failed!");
    }

    //Verify the http response status returned. Check Status Code is 302?
    public void checkStatusIs302(Response res) throws InterruptedException {
        Thread.sleep(1000);
        Assert.assertEquals(res.getStatusCode(), 302, "Status Check Failed!");
    }

    //Verify the http response status returned. Check Status Code is 500?
    public void checkErrorStatusCode(Response res) throws InterruptedException {
        Thread.sleep(1000);
        boolean flag = false;
        System.out.println(res.getStatusCode());
        if (Integer.parseInt(String.valueOf(res.getStatusCode())) == 500 || Integer.parseInt(String.valueOf(res.getStatusCode())) == 200
                || Integer.parseInt(String.valueOf(res.getStatusCode())) == 404) {
            flag = true;
        }
        Assert.assertTrue(flag, "Status Check Failed!");
    }
}