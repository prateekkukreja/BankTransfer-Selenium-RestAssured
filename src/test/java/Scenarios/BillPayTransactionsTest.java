package Scenarios;

import Core.BaseClass;
import Enums.CONSTANTCODES;
import Steps.BillPaySteps;
import org.testng.annotations.Test;

import java.io.IOException;

public class BillPayTransactionsTest extends BaseClass {

    @Test
    public void BillPayLaunchTest() throws IOException, InterruptedException {

        BillPaySteps steps = new BillPaySteps();
        steps.billPaySteps();
    }

    @Test
    public void BillPaySavingsToCheckingTest() throws IOException, InterruptedException {

        BillPaySteps steps = new BillPaySteps();
        steps.billPaySteps(CONSTANTCODES.SAVINGS, CONSTANTCODES.CHECKING);
    }

    @Test
    public void BillPayCheckingToSavingsTest() throws IOException, InterruptedException {

        BillPaySteps steps = new BillPaySteps();
        steps.billPaySteps(CONSTANTCODES.CHECKING, CONSTANTCODES.SAVINGS);
    }

    @Test
    public void BillPayInvalidPayerToSavingsTest() throws IOException, InterruptedException {

        BillPaySteps steps = new BillPaySteps();
        steps.billPaySteps("demo", CONSTANTCODES.SAVINGS);
    }

    @Test
    public void BillPayInvalidPayerToCheckingTest() throws IOException, InterruptedException {

        BillPaySteps steps = new BillPaySteps();
        steps.billPaySteps("demo", CONSTANTCODES.CHECKING);
    }

    @Test
    public void BillPayCheckingToInvalidPayeeTest() throws IOException, InterruptedException {

        BillPaySteps steps = new BillPaySteps();
        steps.billPaySteps(CONSTANTCODES.CHECKING, "demo");
    }

    @Test
    public void BillPaySavingsToInvalidPayeeTest() throws IOException, InterruptedException {

        BillPaySteps steps = new BillPaySteps();
        steps.billPaySteps(CONSTANTCODES.SAVINGS, "demo");
    }
}
