package Requests;

import ConfigManagers.LocalConfigs;
import io.restassured.builder.RequestSpecBuilder;
import org.json.simple.JSONObject;

@SuppressWarnings("unchecked")
public class BillPayXfer extends BaseApi {

    final JSONObject jsonObject = new JSONObject();

    public BillPayXfer(int payerAcct, int payeeAcct) {
        setMethod(MethodType.POST);

        RequestSpecBuilder request = getRequestSpecBuilder();

        request.setBaseUri(LocalConfigs.URL);
        request.setBasePath("services_proxy/bank/billpay");

        request.addQueryParam("accountId", payerAcct);
        request.addQueryParam("amount", 200);

        request.addHeader("cookie", "JSESSIONID=" + helperActions.getTokenFromFile());
        request.addHeader("content-type", "application/json;charset=UTF-8");

        JSONObject map = new JSONObject();
        map.put("city", LocalConfigs.CITY);
        map.put("state", LocalConfigs.STATE);
        map.put("street", LocalConfigs.STREET);
        map.put("zipCode", LocalConfigs.ZIPCODE);

        jsonObject.put("name", LocalConfigs.NAME);
        jsonObject.put("phone", LocalConfigs.PHONE);
        jsonObject.put("accountNumber", payeeAcct);
        jsonObject.put("address", map);

        request.setBody(jsonObject);

    }
}
