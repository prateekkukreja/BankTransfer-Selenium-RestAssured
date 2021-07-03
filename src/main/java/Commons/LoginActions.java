package Commons;

import Enums.CONSTANTCODES;
import ConfigManagers.LocalConfigs;
import PageObjects.LoginPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import java.util.concurrent.TimeUnit;

public class LoginActions {

    LoginPage loginPage = new LoginPage();

    public void loginWithValidUser(WebDriver driver) {
        try {
            loginWithCreds(driver, LocalConfigs.USER, LocalConfigs.PASS);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    public void loginWithInValidUser(WebDriver driver) {
        try {
            loginWithInvalidCreds(driver, "temp", "temp123");
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    public void loginWithInvalidCreds(WebDriver driver, String user, String pass) {
        try {
            driver.findElement(By.xpath(loginPage.getUserNameTextBox())).click();
            driver.findElement(By.xpath(loginPage.getUserNameTextBox())).sendKeys(user);

            driver.findElement(By.xpath(loginPage.getPassWordTextBox())).click();
            driver.findElement(By.xpath(loginPage.getPassWordTextBox())).sendKeys(pass);

            driver.findElement(By.xpath(loginPage.getSubmit())).click();
            Assert.assertEquals(driver.findElement(By.xpath(loginPage.getError())).getText(), CONSTANTCODES.LoginERROR);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }


    public void loginWithCreds(WebDriver driver, String user, String pass) {
        try {
            driver.findElement(By.xpath(loginPage.getUserNameTextBox())).click();
            driver.findElement(By.xpath(loginPage.getUserNameTextBox())).sendKeys(user);

            driver.findElement(By.xpath(loginPage.getPassWordTextBox())).click();
            driver.findElement(By.xpath(loginPage.getPassWordTextBox())).sendKeys(pass);

            driver.findElement(By.xpath(loginPage.getSubmit())).click();
            driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }
}