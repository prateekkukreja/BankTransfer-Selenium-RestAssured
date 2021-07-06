package Core;

import ConfigManagers.LocalConfigs;
import Requests.GetToken;
import Utils.ClientUtil;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import io.restassured.response.Response;
import org.apache.commons.io.FileUtils;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;

public abstract class BaseClass {

    //    ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    public ExtentReports extent;
    public ExtentTest extentTest;
    public ExtentHtmlReporter htmlReporter;

    static {
        initDirs();
    }

    //Instantiate a Helper Test Methods (ClientUtils) Object
    final Utils.ClientUtil ClientUtil = new ClientUtil();

    @BeforeSuite
    public void init() throws IOException, InterruptedException {

        File file = new File(LocalConfigs.ACCOUNTS_DIR + "Accounts.txt");
        if (file.exists()) {
            file.delete();
            file.createNewFile();
        }

        GetToken getToken = new GetToken();
        Response response = getToken.executeGenerateToken();

        ClientUtil.checkStatusIs200(response);
    }

    @BeforeClass
    public void beforeClass() {
        htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "/reports/extent.html");
        htmlReporter.config().setEncoding("UTF-8");
        htmlReporter.config().setDocumentTitle("Automation Report");
        htmlReporter.config().setReportName("Automated Test Results");
        htmlReporter.config().setTheme(Theme.DARK);
        htmlReporter.config().setAutoCreateRelativePathMedia(true);

        extent = new ExtentReports();
        extent.setSystemInfo("Prateek", "Automation");
        extent.setSystemInfo("Browser", "Chrome");
        extent.setSystemInfo("os", "MAC");
        extent.setSystemInfo("Environment", "Test");
        extent.attachReporter(htmlReporter);
    }

    @BeforeMethod
    public synchronized void setUpExtentRpt(Method method) {
        extentTest = extent.createTest((this.getClass().getSimpleName() + " :: " + method.getName()),
                method.getName());
    }

    @AfterMethod
    public void afterMethod(ITestResult result) {
        try {
            String methodName = result.getMethod().getMethodName();
            if (result.getStatus() == ITestResult.FAILURE) {
                String exceptionMsg = Arrays.toString(result.getThrowable().getStackTrace());
                extentTest.fail("<details><summary><b><font color=red>Exception occured, click to see details:"
                        + "</font></b></summary>" + exceptionMsg.replaceAll(",", "<br>")
                        + "</details> \n");
                Thread.sleep(3000);
                extentTest.fail("<b><font color=red>" + "Screenshot of failure" + "</font></b>");
                String logtest = "<b>Test Method " + methodName + " Failed </b>";
                Markup m = MarkupHelper.createLabel(logtest, ExtentColor.RED);
                extentTest.log(Status.FAIL, m);
            } else if (result.getStatus() == ITestResult.SUCCESS) {
                String logtest = "<b>Test Method " + methodName + " Passed </b>";
                Markup m = MarkupHelper.createLabel(logtest, ExtentColor.GREEN);
                extentTest.log(Status.PASS, m);
            } else if (result.getStatus() == ITestResult.SKIP) {
                String logtest = "<b>Test Method " + methodName + " Skipped </b>";
                Markup m = MarkupHelper.createLabel(logtest, ExtentColor.YELLOW);
                extentTest.log(Status.SKIP, m);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @AfterClass
    public void terminateDriver() {
        extent.flush();
    }

    public static synchronized void initDirs() {
        try {
            File newDir = new File(System.getProperty("user.dir") + "/src/main/resources/Accounts/");
            FileUtils.forceMkdir(newDir);
            FileUtils.cleanDirectory(newDir);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}