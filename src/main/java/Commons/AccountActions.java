package Commons;

import Enums.CONSTANTCODES;
import ConfigManagers.LocalConfigs;
import Core.HelperActions;
import PageObjects.OpenNewAccount;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class AccountActions {

    HelperActions helperActions = new HelperActions();
    OpenNewAccount openNewAccount = new OpenNewAccount();
    private String type = "";
    private int amount = 0;

    public boolean verifyAcctTxnDetails(WebDriver driver, int Account, String AcctType) {
        boolean flag = false;
        try {
            Thread.sleep(3000);
            WebElement xPath = driver.findElement(By.xpath(openNewAccount.getAccountId()));
            helperActions.waitUntilElementVisible(driver, xPath);
            int onScreenAcct = Integer.parseInt(xPath.getText());
            Assert.assertEquals(onScreenAcct, Account);

            xPath = driver.findElement(By.xpath(openNewAccount.getAcctType()));
            String onScreenAcctType = xPath.getText();
            List<WebElement> msg = driver.findElements(By.xpath(openNewAccount.getTransactions()));

            for (WebElement e : msg) {
                if (e.getAttribute("innerHTML").equalsIgnoreCase(CONSTANTCODES.BILL_PAYMENT)) {
                    Assert.assertTrue(e.getAttribute("innerHTML").equalsIgnoreCase(CONSTANTCODES.BILL_PAYMENT));
                }
            }

            getCurrentUserTxnDetails(AcctType);

            xPath = driver.findElement(By.xpath("//td[contains(@ng-if,'" + type + "') and @class='ng-binding ng-scope']"));
            String txnAmt = xPath.getAttribute("innerHTML");
            NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.US);
            Number amt = numberFormat.parse(txnAmt);

            Assert.assertEquals(Integer.parseInt(amt.toString()), amount);
            if (onScreenAcctType.equalsIgnoreCase(AcctType)) {
                flag = true;
                System.out.println("New " + AcctType + " Account creation verified");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
        return flag;
    }

//    public boolean verifyNewSavingsAcct(WebDriver driver, int Account, String AcctType) {
//        boolean flag = false;
//        try {
//            Thread.sleep(3000);
//            WebElement xPath = driver.findElement(By.xpath("//td[@id='accountId' and @class='ng-binding']"));
//            helperActions.waitUntilElementVisible(driver, xPath);
//            int onScreenAcct = Integer.parseInt(xPath.getText());
//            xPath = driver.findElement(By.xpath("//td[@id='accountType' and @class='ng-binding']"));
//            String onScreenAcctType = xPath.getText();
//
//            xPath = driver.findElement(By.xpath("//td[contains(@ng-if,'Debit') and @class='ng-binding ng-scope']"));
//            String txnAmt = xPath.getAttribute("innerHTML");
//            NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.US);
//            Number amt = numberFormat.parse(txnAmt);
//            Assert.assertEquals(onScreenAcct, Account);
//            Assert.assertEquals(Integer.parseInt(amt.toString()), 200);
//            if (onScreenAcctType.equalsIgnoreCase(AcctType)) {
//                flag = true;
////                System.out.println("New " + AcctType + " Account creation verified");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            Assert.fail();
//        }
//        return flag;
//    }

    public boolean verifyNewAcct(WebDriver driver, int Account, String AcctType) {
        boolean flag = false;
        try {
            Thread.sleep(3000);
            WebElement xPath = driver.findElement(By.xpath(openNewAccount.getAccountId()));
            helperActions.waitUntilElementVisible(driver, xPath);
            int onScreenAcct = Integer.parseInt(xPath.getText());
            xPath = driver.findElement(By.xpath(openNewAccount.getAcctType()));
            String onScreenAcctType = xPath.getText();
            if (onScreenAcct == Account && onScreenAcctType.equalsIgnoreCase(AcctType)) {
                flag = true;
//                System.out.println("New " + AcctType + " Account creation verified");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
        return flag;
    }

    //creating Checking Account with any of the existing account
    public void createCheckingAccount(WebDriver driver) {
        try {
            helperActions.waitUntilElementVisible(driver, driver.findElement(By.linkText(CONSTANTCODES.NEW_ACCT_LINK)));
            driver.findElement(By.linkText(CONSTANTCODES.NEW_ACCT_LINK)).click();
            Select accountType = new Select(driver.findElement(By.xpath(openNewAccount.getAccountType())));
            accountType.selectByVisibleText(CONSTANTCODES.CHECKING);

            WebElement xPath = driver.findElement(By.xpath(openNewAccount.getFromAccount()));
            helperActions.waitUntilElementClickable(driver, xPath);
            xPath.click();

            helperActions.SelectDropDown(driver, xPath);
            xPath = driver.findElement(By.xpath(openNewAccount.getSubmit()));
            helperActions.waitUntilElementClickable(driver, xPath);
            xPath.click();

            CONSTANTCODES.CHECKING_Acct = Integer.parseInt(driver.findElement(By.xpath(openNewAccount.getNewAccountID())).getAttribute("innerHTML"));
            driver.findElement(By.xpath(openNewAccount.getNewAccountID())).click();

            synchronized (this) {
                helperActions.writeToFile("currAcct:" + CONSTANTCODES.CHECKING_Acct);
            }
            Assert.assertTrue(verifyNewAcct(driver, CONSTANTCODES.CHECKING_Acct, CONSTANTCODES.CHECKING));
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
//        return CONSTANTCODES.CHECKING_Acct;
    }

    //creating Savings Account with any of the existing account
    public void createSavingsAccount(WebDriver driver) {
        try {
            helperActions.waitUntilElementVisible(driver, driver.findElement(By.linkText(CONSTANTCODES.NEW_ACCT_LINK)));
            driver.findElement(By.linkText(CONSTANTCODES.NEW_ACCT_LINK)).click();
            Select accountType = new Select(driver.findElement(By.xpath(openNewAccount.getAccountType())));
            accountType.selectByVisibleText(CONSTANTCODES.SAVINGS);

            WebElement xPath = driver.findElement(By.xpath(openNewAccount.getFromAccount()));
            helperActions.waitUntilElementClickable(driver, xPath);
            xPath.click();

            helperActions.SelectDropDown(driver, xPath);

            xPath = driver.findElement(By.xpath(openNewAccount.getSubmit()));
            helperActions.waitUntilElementClickable(driver, xPath);
            xPath.click();
//            driver.findElement(By.xpath("//input[@type='submit' and @class='button']")).click();
            CONSTANTCODES.SAVINGS_Acct = Integer.parseInt(driver.findElement(By.xpath(openNewAccount.getNewAccountID())).getAttribute("innerHTML"));
            driver.findElement(By.xpath(openNewAccount.getNewAccountID())).click();

            synchronized (this) {
                helperActions.writeToFile("savAcct:" + CONSTANTCODES.SAVINGS_Acct);
            }

            verifyNewAcct(driver, CONSTANTCODES.SAVINGS_Acct, CONSTANTCODES.SAVINGS);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
//        return CONSTANTCODES.SAVINGS_Acct;
    }

    public void getCurrentUserTxnDetails(String AcctType) {
        if (AcctType.equalsIgnoreCase(CONSTANTCODES.CHECKING) && CONSTANTCODES.PAYER.equalsIgnoreCase("CHECKING")) {
            type = "Debit";
            amount = LocalConfigs.INIT_AMOUNT;
        } else if (AcctType.equalsIgnoreCase("CHECKING") && CONSTANTCODES.PAYER.equalsIgnoreCase("SAVINGS")) {
            type = "Credit";
            amount = LocalConfigs.MIN_CREDIT;
        } else if (AcctType.equalsIgnoreCase("SAVINGS") && CONSTANTCODES.PAYER.equalsIgnoreCase("SAVINGS")) {
            type = "Debit";
            amount = LocalConfigs.INIT_AMOUNT;
        } else if (AcctType.equalsIgnoreCase("SAVINGS") && CONSTANTCODES.PAYER.equalsIgnoreCase("CHECKING")) {
            type = "Credit";
            amount = LocalConfigs.MIN_CREDIT;
        }

    }

}
