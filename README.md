# BankTransfer-Selenium-RestAssured
BankTransfer-Selenium-RestAssured covers UI End to End Test cases of Login, Open Accounts, Funds Transfer functionality testing using Selenium Webdriver and Java. On the backend testing Rest API's with Rest Assured. 

Mentioned below are the steps to execute test cases covering Login, Open Accounts, Funds Transfer functionalities.



For UI end to end tests:


### Steps

Clone the example application and install dependencies:

```bash
$ git clone git@github.com:prateekkukreja/BankTransfer-Selenium-RestAssured.git
$ cd AxelerantAssessment
$ mvn clean install
```

The example app and its tests will now be ready to go. You can explore the results from the extent report 
by opening the
[`reports/extent.html`](https://github.com/prateekkukreja/BankTransfer-Selenium-RestAssured/blob/restAutomation/reports/extent.html)
file in a browser.

```bash
$ To run tests in multi-thread environment, please ensure to use following parameter names and values
<test verbose="1" preserve-order="false" name="Browser">
        <parameter name="browserName" value="chrome"/>
        <classes>
            <class name="Folder.ClassName"/>
        </classes>
    </test>
    <test verbose="1" preserve-order="false" name="Headless">
        <parameter name="browserName" value="headless"/>
        <classes>
            <class name="Folder.ClassName"/>
        </classes>
    </test>
```




For Backend end to end API tests:

### Steps

Clone the example application and install dependencies:

```bash
$ git clone git@github.com:prateekkukreja/BankTransfer-Selenium-RestAssured.git
$ cd AxelerantAssessment
$ git checkout restAutomation
$ mvn clean install
```




The app tests will now be ready to go. You can explore the results from the extent report 
by opening the
[`reports/extent.html`](https://github.com/prateekkukreja/BankTransfer-Selenium-RestAssured/blob/restAutomation/reports/extent.html)
file in a browser.






In the shell window you're working in, export the token environment variable:

**Unix**

``` shell
$ cd ~/AxelerantAssessment
$ mvn test
```





Failure Screenshots can be viewed in Exntent Report as well as in an out folder
[`reports/TestMethod`](https://github.com/prateekkukreja/BankTransfer-Selenium-RestAssured/blob/master/reports/FundsTransferTestSavToChk_Wed_Jul_07_02_39_02_IST_2021.png)
file in a browser.




![image](https://user-images.githubusercontent.com/44745059/124666311-7d7cf280-decb-11eb-9e01-67d69241f006.png)



![image](https://user-images.githubusercontent.com/44745059/124667490-195b2e00-decd-11eb-8569-1accc53ef6cf.png)






### Finished! 😀
