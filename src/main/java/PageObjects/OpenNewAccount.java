package PageObjects;

import Core.ReadXML;
import UIElement.*;

public class OpenNewAccount extends BasePage {

    private Select accountType;
    private Select fromAccount;
    private Button submit;
    private ElementList newAccountID;
    private ElementList accountId;
    private ElementList acctType;
    private ElementList transactions;

    public OpenNewAccount() {
        super(OpenNewAccount.class.getSimpleName());

        accountType = new Select(
                ReadXML.getElementLocator(getPageName(), "AccountType"),
                getPageName(), "AccountType");
        fromAccount = new Select(ReadXML.getElementLocator(getPageName(), "FromAccount"),
                getPageName(), "FromAccount");
        submit = new Button(ReadXML.getElementLocator(getPageName(), "Submit"),
                getPageName(), "Submit");
        newAccountID = new ElementList(ReadXML.getElementLocator(getPageName(), "NewAccountID"),
                getPageName(), "NewAccountID");
        accountId = new ElementList(ReadXML.getElementLocator(getPageName(), "AccountId"),
                getPageName(), "AccountId");
        acctType = new ElementList(ReadXML.getElementLocator(getPageName(), "AcctType"),
                getPageName(), "AcctType");
        transactions = new ElementList(ReadXML.getElementLocator(getPageName(), "Transactions"),
                getPageName(), "Transactions");
    }

    public String getTransactions() {
        return transactions.getBy().toString().replace("By.xpath: ", "");
    }

    public String getAccountId() {
        return accountId.getBy().toString().replace("By.xpath: ", "");
    }

    public String getAcctType() {
        return acctType.getBy().toString().replace("By.xpath: ", "");
    }

    public String getAccountType() {
        return accountType.getBy().toString().replace("By.xpath: ", "");
    }

    public String getNewAccountID() {
        return newAccountID.getBy().toString().replace("By.xpath: ", "");
    }

    public String getFromAccount() {
        return fromAccount.getBy().toString().replace("By.xpath: ", "");
    }

    public String getSubmit() {
        return submit.getBy().toString().replace("By.xpath: ", "");
    }


}
