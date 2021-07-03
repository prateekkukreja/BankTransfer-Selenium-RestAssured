package PageObjects;

import Core.ReadXML;
import UIElement.*;

public class BillPayPage extends BasePage {

    private TextBox ZipCode;
    private TextBox PayeeAcct;
    private TextBox VerifyAcct;
    private TextBox Amount;
    private TextBox FromAcctID;
    private TextBox phone;
    private Button Submit;

    public BillPayPage() {
        super(PageObjects.OpenNewAccount.class.getSimpleName());

        ZipCode = new TextBox(
                ReadXML.getElementLocator(getPageName(), "AccountType"),
                getPageName(), "AccountType");
        PayeeAcct = new TextBox(ReadXML.getElementLocator(getPageName(), "FromAccount"),
                getPageName(), "FromAccount");
        VerifyAcct = new TextBox(ReadXML.getElementLocator(getPageName(), "Submit"),
                getPageName(), "Submit");
        Amount = new TextBox(ReadXML.getElementLocator(getPageName(), "NewAccountID"),
                getPageName(), "NewAccountID");
        FromAcctID = new TextBox(ReadXML.getElementLocator(getPageName(), "AccountId"),
                getPageName(), "AccountId");
        phone = new TextBox(ReadXML.getElementLocator(getPageName(), "AcctType"),
                getPageName(), "AcctType");
        Submit = new Button(ReadXML.getElementLocator(getPageName(), "Transactions"),
                getPageName(), "Transactions");
    }

    public String getZipCode() {
        return ZipCode.getBy().toString().replace("By.xpath: ", "");
    }

    public String getPayeeAcct() {
        return PayeeAcct.getBy().toString().replace("By.xpath: ", "");
    }

    public String getVerifyAcct() {
        return VerifyAcct.getBy().toString().replace("By.xpath: ", "");
    }

    public String getAmount() {
        return Amount.getBy().toString().replace("By.xpath: ", "");
    }

    public String getFromAcctID() {
        return FromAcctID.getBy().toString().replace("By.xpath: ", "");
    }

    public String getPhone() {
        return phone.getBy().toString().replace("By.xpath: ", "");
    }

    public String getSubmit() {
        return Submit.getBy().toString().replace("By.xpath: ", "");
    }


}

