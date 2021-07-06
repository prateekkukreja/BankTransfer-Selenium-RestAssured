package Scenarios;

import Enums.TxnTypes;
import Commons.AccountActions;
import Commons.BillPayActions;
import Commons.LoginActions;
import Core.BaseClass;
import org.testng.annotations.Test;

// Scenario #3
public class BillPayFeatureTests extends BaseClass {

    final LoginActions login = new LoginActions();
    final BillPayActions billPage = new BillPayActions();
    final AccountActions accts = new AccountActions();

    //Automate the verification of Bill Page
    @Test
    public void BillPayPageVerificationTest() {
        login.loginWithValidUser(getDriver());
        billPage.verifyBillPageElements(getDriver());
    }

    //Fund xfer between newly created Accts Checking to Savings
    @Test
    public void FundsTransferTestChkToSav() {
        login.loginWithValidUser(getDriver());
        accts.createCheckingAccount(getDriver());
        accts.createSavingsAccount(getDriver());
        billPage.makeFundTransfer(getDriver(), TxnTypes.CHECKINGTOSAVINGS);
    }

    //Fund xfer between newly created Accts Savings to Checking
    @Test
    public void FundsTransferTestSavToChk() {
        login.loginWithValidUser(getDriver());
        accts.createCheckingAccount(getDriver());
        accts.createSavingsAccount(getDriver());
        billPage.makeFundTransfer(getDriver(), TxnTypes.SAVINGSTOCHECKING);
    }

    //verifying post transaction details
//    @Test(dependsOnMethods = {"FundsTransferTest"})
    @Test
    public void verifyTransactionsSavToChk() {
        login.loginWithValidUser(getDriver());
        accts.createCheckingAccount(getDriver());
        accts.createSavingsAccount(getDriver());
        billPage.makeFundTransfer(getDriver(), TxnTypes.SAVINGSTOCHECKING);
        billPage.verifyTransactionDetails(getDriver());
    }

    @Test
    public void verifyTransactionsChkToSav() {
        login.loginWithValidUser(getDriver());
        accts.createCheckingAccount(getDriver());
        accts.createSavingsAccount(getDriver());
        billPage.makeFundTransfer(getDriver(), TxnTypes.CHECKINGTOSAVINGS);
        billPage.verifyTransactionDetails(getDriver());
    }
}