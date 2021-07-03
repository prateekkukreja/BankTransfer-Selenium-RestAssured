package Core;

import ConfigManagers.LocalConfigs;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

public class HelperActions {

    public void waitUntilElementVisible(WebDriver driver, WebElement element) {
        try {
            Thread.sleep(3000);
            WebDriverWait wait = new WebDriverWait(driver, Integer.parseInt(LocalConfigs.MAX_WAIT_TIME));
            wait.until(ExpectedConditions.visibilityOf(element));
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    public void waitUntilElementClickable(WebDriver driver, WebElement element) {
        try {
            Thread.sleep(3000);
            WebDriverWait wait = new WebDriverWait(driver, Integer.parseInt(LocalConfigs.MAX_WAIT_TIME));
            wait.until(ExpectedConditions.elementToBeClickable(element));
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    public void SelectDropDown(WebDriver driver, WebElement xPath) {
        try {
            Thread.sleep(3000);
            Select objSel = new Select(xPath);
            List<WebElement> weblist = objSel.getOptions();
            int iCnt = weblist.size();
            Random num = new Random();
            int iSelect = num.nextInt(iCnt);
            driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
            objSel.selectByIndex(iSelect);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    public void SelectDropDownByValue(WebDriver driver, WebElement xPath, String value) {
        try {
            Thread.sleep(3000);
            Select objSel = new Select(xPath);
            driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
            objSel.selectByValue(value);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    public void writeToFile(String str) {
        try {
            File file = new File(LocalConfigs.ACCOUNTS_DIR + "Accounts.txt");
            BufferedWriter out = new BufferedWriter(new FileWriter(file, true));
            out.write(str + "\n");
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    public List<String> readFile() {
        List<String> list = new ArrayList<>();
        try {
            String fileName = LocalConfigs.ACCOUNTS_DIR + "Accounts.txt";
            try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
                stream.forEach(list::add);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
        return list;
    }
}
