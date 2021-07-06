package PageObjects;

import Core.ReadXML;
import UIElement.BasePage;
import UIElement.Button;
import UIElement.Error;
import UIElement.TextBox;

public class LoginPage extends BasePage {

    private final TextBox userNameTextBox;
    private TextBox passWordTextBox;
    private final Button submit;
    private final Error error;


    public LoginPage() {
        super(LoginPage.class.getSimpleName());

        userNameTextBox = new TextBox(
                ReadXML.getElementLocator(getPageName(), "UserName"),
                getPageName(), "UserName");
        passWordTextBox = new TextBox(ReadXML.getElementLocator(getPageName(), "PassWord"),
                getPageName(), "PassWord");
        submit = new Button(ReadXML.getElementLocator(getPageName(), "Submit"),
                getPageName(), "Submit");
        error = new Error(ReadXML.getElementLocator(getPageName(), "Error"),
                getPageName(), "Error");

    }

    public String getError() {
        return error.getBy().toString().replace("By.xpath: ", "");
    }

    public String getUserNameTextBox() {
        return userNameTextBox.getBy().toString().replace("By.xpath: ", "");
    }

    public String getPassWordTextBox() {
        return passWordTextBox.getBy().toString().replace("By.xpath: ", "");
    }

    public void setPassWordTextBox(TextBox passWordTextBox) {
        this.passWordTextBox = passWordTextBox;
    }

    public String getSubmit() {
        return submit.getBy().toString().replace("By.xpath: ", "");
    }

}
