package Core;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Date;

import ConfigManagers.LocalConfigs;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.util.concurrent.TimeUnit;

public abstract class BaseClass {

    final ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    public ExtentReports extent;
    public ExtentTest extentTest;
    public ExtentHtmlReporter htmlReporter;

    public void setDriver(WebDriver driver) {
        this.driver.set(driver);
    }

    public WebDriver getDriver() {
        return this.driver.get();
    }

    static {
        initDirs();
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
    @Parameters(value = {"browserName"})
    public synchronized void setUpExtentRpt(@Optional String browserName, Method method) throws IOException {
        extentTest = extent.createTest((this.getClass().getSimpleName() + " :: " + method.getName()),
                method.getName());
        if (browserName == null) {
            browserName = "chrome";
        }
        setupDriver(browserName);
        getDriver().get(LocalConfigs.URL);
        getDriver().manage().window().maximize();
        getDriver().manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        //     System.out.println("Opening website :: " + LocalConfigs.URL);
        File file = new File(LocalConfigs.ACCOUNTS_DIR + "Accounts.txt");
        if (file.exists()) {
            file.delete();
            file.createNewFile();
        }
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
                String path = takeScreenshot(result.getMethod().getMethodName());
                try {
                    Thread.sleep(3000);
                    extentTest.fail("<b><font color=red>" + "Screenshot of failure" + "</font></b>",
                            MediaEntityBuilder.createScreenCaptureFromPath(path).build());
                } catch (IOException e) {
                    e.printStackTrace();
                    extentTest.fail("Test failed, cannot attach SS");
                }
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
        getDriver().quit();
    }

    public static String getScreenShotName(String methodName) {
        Date d = new Date();
        return methodName + "_" + d.toString().replace(":", "_").replace(" ", "_") + ".png";
    }

    private String takeScreenshot(String methodName) {
        String fileName = getScreenShotName(methodName);
        File filepath = new File(System.getProperty("user.dir") + "/reports/");
        String path = filepath + "/" + fileName;

        try {
            File scrshot = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(scrshot, new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return path;
    }

    @AfterSuite
    public void terminate() {
        extent.flush();
        getDriver().quit();
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

    public void setupDriver(String browserName) throws MalformedURLException {
        ChromeOptions options = new ChromeOptions();
        if (browserName.equalsIgnoreCase("chrome")) {
            System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/src/main/resources/chromedriver");
            options.addArguments("--disable-notifications");
            options.addArguments("--start-maximized");
            options.addArguments("--disable-blink-features=AutomationControlled");
            options.addArguments("--ignore-certificate-errors");
            options.addArguments("--no-sandbox");
            setDriver(new ChromeDriver(options));
        } else if (browserName.equalsIgnoreCase("headless")) {
            System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/src/main/resources/chromedriver");
            options.addArguments("headless");
            options.addArguments("window-size=1400,800");
            options.addArguments("disable-gpu");
            setDriver(new ChromeDriver(options));
        } else if (browserName.equalsIgnoreCase("IE")) {
            System.setProperty("webdriver.ie.driver", System.getProperty("user.dir") + "/src/main/resources/geckodriver");
            setDriver(new InternetExplorerDriver());
        } else if (browserName.equalsIgnoreCase("firefox")) {
            System.setProperty("webdriver.firefox.marionette", System.getProperty("user.dir") + "/src/main/resources/geckodriver");
            setDriver(new FirefoxDriver());
        } else if (browserName.equalsIgnoreCase("remote")) {
            ChromeOptions cap = new ChromeOptions();
            cap.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR,
                    UnexpectedAlertBehaviour.IGNORE);
            setDriver(new RemoteWebDriver(new URL("http://hub:4444/wd/hub"), cap));
        }
    }
}