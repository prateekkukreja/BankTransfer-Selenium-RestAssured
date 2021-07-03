package Commons;

import Enums.CONSTANTCODES;
import Core.HelperActions;
import PageObjects.BillPayPage;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.List;

public class BillPayActions {

    HelperActions helperActions = new HelperActions();
    AccountActions accountActions = new AccountActions();
    BillPayPage billPayPage = new BillPayPage();

    String[] expected = {"payee.name", "payee.address.street", "payee.address.city", "payee.address.state",
            "payee.address.zipCode", "payee.accountNumber", "verifyAccount", "amount"};

    //Generic function to check bill page elements and transaction details
    public void verifyTransactionDetails(WebDriver driver) {

        try {
            helperActions.waitUntilElementVisible(driver, driver.findElement(By.linkText(CONSTANTCODES.ACCOUNTS_OVERVIEW)));
            driver.findElement(By.linkText(CONSTANTCODES.ACCOUNTS_OVERVIEW)).click();

            //Scroll down to select account numbers on Accounts Overview page
            ((JavascriptExecutor) driver)
                    .executeScript("window.scrollTo(0, document.body.scrollHeight)");

            WebElement xPath;
            if (CONSTANTCODES.PAYER.equalsIgnoreCase("CHECKING")) {

                xPath = driver.findElement(By.xpath("//a[contains(@href,'" + CONSTANTCODES.CHECKING_Acct + "') and @class='ng-binding']"));
                helperActions.waitUntilElementVisible(driver, xPath);
                xPath.click();

                accountActions.verifyAcctTxnDetails(driver, CONSTANTCODES.CHECKING_Acct, CONSTANTCODES.CHECKING);

                helperActions.waitUntilElementVisible(driver, driver.findElement(By.linkText(CONSTANTCODES.ACCOUNTS_OVERVIEW)));
                driver.findElement(By.linkText(CONSTANTCODES.ACCOUNTS_OVERVIEW)).click();

                xPath = driver.findElement(By.xpath("//a[contains(@href,'" + CONSTANTCODES.SAVINGS_Acct + "') and @class='ng-binding']"));
                helperActions.waitUntilElementVisible(driver, xPath);
                xPath.click();

                accountActions.verifyAcctTxnDetails(driver, CONSTANTCODES.SAVINGS_Acct, CONSTANTCODES.SAVINGS);

            } else if (CONSTANTCODES.PAYER.equalsIgnoreCase("SAVINGS")) {

                xPath = driver.findElement(By.xpath("//a[contains(@href,'" + CONSTANTCODES.SAVINGS_Acct + "') and @class='ng-binding']"));
                helperActions.waitUntilElementVisible(driver, xPath);
                xPath.click();

                accountActions.verifyAcctTxnDetails(driver, CONSTANTCODES.SAVINGS_Acct, CONSTANTCODES.SAVINGS);

                helperActions.waitUntilElementVisible(driver, driver.findElement(By.linkText(CONSTANTCODES.ACCOUNTS_OVERVIEW)));
                driver.findElement(By.linkText(CONSTANTCODES.ACCOUNTS_OVERVIEW)).click();

                xPath = driver.findElement(By.xpath("//a[contains(@href,'" + CONSTANTCODES.CHECKING_Acct + "') and @class='ng-binding']"));
                helperActions.waitUntilElementVisible(driver, xPath);
                xPath.click();

                accountActions.verifyAcctTxnDetails(driver, CONSTANTCODES.CHECKING_Acct, CONSTANTCODES.CHECKING);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //initiating fund xfer from new Checking Acct to Savings Acct
    public void makeFundTransfer(WebDriver driver, Enum TxnType) {
        boolean flag = false;
        try {
            if (TxnType.toString().equalsIgnoreCase("CHECKINGTOSAVINGS")) {
                CONSTANTCODES.PAYER = "CHECKING";
                CONSTANTCODES.PAYEE = "SAVINGS";
                flag = true;
            } else if (TxnType.toString().equalsIgnoreCase("SAVINGSTOCHECKING")) {
                CONSTANTCODES.PAYER = "SAVINGS";
                CONSTANTCODES.PAYEE = "CHECKING";
            }

            helperActions.waitUntilElementVisible(driver, driver.findElement(By.linkText(CONSTANTCODES.BILL_PAY_LINK)));
            driver.findElement(By.linkText(CONSTANTCODES.BILL_PAY_LINK)).click();

            getNewGeneratedAccounts();

            //selecting newly created Savings Account as Payer and Checking Acct as Payee
            if (!flag) {
                for (String element : expected) {
                    if (element.contains("zipCode")) {
                        driver.findElement(By.xpath("//input[@ng-model='payee.address.zipCode' and @name='payee.address.zipCode']")).sendKeys("110001");
                    } else if (element.contains("accountNumber")) {
                        driver.findElement(By.xpath("//input[@ng-model='payee.accountNumber' and @name='payee.accountNumber']")).sendKeys(String.valueOf(CONSTANTCODES.CHECKING_Acct));
                    } else if (element.contains("verifyAccount")) {
                        driver.findElement(By.xpath("//input[@ng-model='verifyAccount' and @name='verifyAccount']")).sendKeys(String.valueOf(CONSTANTCODES.CHECKING_Acct));
                    } else if (element.contains("amount")) {
                        driver.findElement(By.xpath("//input[@ng-model='amount' and @name='amount']")).sendKeys("200");
                    } else {
                        driver.findElement(By.xpath("//input[@ng-model='" + element + "' and @name='" + element + "']")).sendKeys("Test");
                    }
                }
                WebElement xPath = driver.findElement(By.xpath("//select[@name='fromAccountId']"));
                helperActions.SelectDropDownByValue(driver, xPath, String.valueOf(CONSTANTCODES.SAVINGS_Acct));
            } else {
                //selecting newly created Checking Account as Payer and Savings Acct as Payee
                for (String element : expected) {
                    if (element.contains("zipCode")) {
                        driver.findElement(By.xpath("//input[@ng-model='payee.address.zipCode' and @name='payee.address.zipCode']")).sendKeys("110001");
                    } else if (element.contains("accountNumber")) {
                        driver.findElement(By.xpath("//input[@ng-model='payee.accountNumber' and @name='payee.accountNumber']")).sendKeys(String.valueOf(CONSTANTCODES.SAVINGS_Acct));
                    } else if (element.contains("verifyAccount")) {
                        driver.findElement(By.xpath("//input[@ng-model='verifyAccount' and @name='verifyAccount']")).sendKeys(String.valueOf(CONSTANTCODES.SAVINGS_Acct));
                    } else if (element.contains("amount")) {
                        driver.findElement(By.xpath("//input[@ng-model='amount' and @name='amount']")).sendKeys("200");
                    } else {
                        driver.findElement(By.xpath("//input[@ng-model='" + element + "' and @name='" + element + "']")).sendKeys("Test");
                    }
                }
                //selecting newly created Savings Account as Payer
                WebElement xPath = driver.findElement(By.xpath("//select[@name='fromAccountId']"));
                helperActions.SelectDropDownByValue(driver, xPath, String.valueOf(CONSTANTCODES.CHECKING_Acct));
            }

            driver.findElement(By.xpath("//input[@name='payee.phoneNumber']")).sendKeys(String.valueOf((long) Math.floor(Math.random() * 9_000_000_000L) + 1_000_000_000L));
            driver.findElement(By.xpath("//input[@type='submit' and @value='Send Payment']")).click();

        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    //Checking bill page elements iteratively
    public void verifyBillPageElements(WebDriver driver) {
        try {
            helperActions.waitUntilElementVisible(driver, driver.findElement(By.linkText(CONSTANTCODES.BILL_PAY_LINK)));
            driver.findElement(By.linkText(CONSTANTCODES.BILL_PAY_LINK)).click();

            AssertBillPageXpaths(driver);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    public void getNewGeneratedAccounts() {

        try {
            List<String> list = helperActions.readFile();
            for (String s : list) {
                if (s.contains("currAcct")) {
                    String[] Accts = s.split(":");
                    CONSTANTCODES.CHECKING_Acct = Integer.parseInt(Accts[1]);
                } else if (s.contains("savAcct")) {
                    String[] Accts = s.split(":");
                    CONSTANTCODES.SAVINGS_Acct = Integer.parseInt(Accts[1]);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    public void AssertBillPageXpaths(WebDriver driver) {
//        boolean flag = false;
        try {
            List<WebElement> allElements = driver.findElements(By.xpath("//input[@class='input ng-pristine ng-untouched ng-valid ng-empty']"));

            if (expected.length != allElements.size()) {
                System.out.println("fail, wrong number of elements found");
            }

            for (int i = 0; i < expected.length; i++) {
                String name = allElements.get(i).getAttribute("name");
                if (name.equals(expected[i])) {
                    System.out.println("passed on: " + name);
                } else {
                    System.out.println("failed on: " + name);
//                    flag = false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
//        return flag;
    }

}