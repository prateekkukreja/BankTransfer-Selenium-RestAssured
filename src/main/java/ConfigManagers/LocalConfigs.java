package ConfigManagers;

import Core.PropertyReader;

public class LocalConfigs {

    public static final String URL;
    public static final String USER;
    public static final String PASS;
    public static final String ACCOUNTS_DIR;
    public static final String FAILTESTRETRYCOUNT;
    public static final int CUST_ID;
    public static final String CITY;
    public static final String STATE;
    public static final String STREET;
    public static final String ZIPCODE;
    public static final String NAME;
    public static final String PHONE;

    static {
        try {
            PropertyReader.getInstance().loader("application.properties");
            URL = PropertyReader.getInstance().getValue("url");
            USER = PropertyReader.getInstance().getValue("user");
            PASS = PropertyReader.getInstance().getValue("pass");
            ACCOUNTS_DIR = System.getProperty("user.dir") + "/src/main/resources/Accounts/";
//            ACCOUNTS_FILE = PropertyReader.getInstance().getValue("accountsFile");
            FAILTESTRETRYCOUNT = System.getProperty("FAILTESTRETRYCOUNT");
            CUST_ID = Integer.parseInt(PropertyReader.getInstance().getValue("custId"));
            CITY = PropertyReader.getInstance().getValue("city");
            STATE = PropertyReader.getInstance().getValue("state");
            STREET = PropertyReader.getInstance().getValue("street");
            ZIPCODE = PropertyReader.getInstance().getValue("zipCode");
            NAME = PropertyReader.getInstance().getValue("name");
            PHONE = PropertyReader.getInstance().getValue("phone");

        } catch (Throwable e) {
            throw new RuntimeException("Revisit configs", e);
        }
    }
}