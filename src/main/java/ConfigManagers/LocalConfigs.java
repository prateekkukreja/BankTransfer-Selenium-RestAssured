package ConfigManagers;

import Core.PropertyReader;

public class LocalConfigs {

    public static final String URL;
    public static final String BROWSER;
    public static final String ENV;
    public static final String USER;
    public static final String PASS;
    public static final String MAX_WAIT_TIME;
    public static final String ACCOUNTS_DIR;
    public static final String FAILTESTRETRYCOUNT;
    public static final int INIT_AMOUNT;
    public static final int MIN_CREDIT;

    static {
        try {
            PropertyReader.getInstance().loader("application.properties");
            URL = PropertyReader.getInstance().getValue("url");
            BROWSER = PropertyReader.getInstance().getValue("browser");
            ENV = PropertyReader.getInstance().getValue("env");
            USER = PropertyReader.getInstance().getValue("user");
            PASS = PropertyReader.getInstance().getValue("pass");
            MAX_WAIT_TIME = PropertyReader.getInstance().getValue("max_wait_time");
            ACCOUNTS_DIR = System.getProperty("user.dir") + "/src/main/resources/Accounts/";
//            ACCOUNTS_FILE = PropertyReader.getInstance().getValue("accountsFile");
            FAILTESTRETRYCOUNT = System.getProperty("FAILTESTRETRYCOUNT");
            INIT_AMOUNT = Integer.parseInt(PropertyReader.getInstance().getValue("init_amt"));
            MIN_CREDIT = Integer.parseInt(PropertyReader.getInstance().getValue("min_cred"));
        } catch (Throwable e) {
            throw new RuntimeException("Revisit configs", e);
        }
    }
}