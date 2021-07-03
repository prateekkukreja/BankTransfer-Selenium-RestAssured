package Retry;

import ConfigManagers.LocalConfigs;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryClass implements IRetryAnalyzer {

    int counter = 0;

    @Override
    public boolean retry(ITestResult result) {
        if (counter < Integer.parseInt(LocalConfigs.FAILTESTRETRYCOUNT)) {
            counter++;
            System.out.println("Retrying : " + result.getName() + " again and count now is : " + counter);
            return true;
        }
        return false;
    }
}
