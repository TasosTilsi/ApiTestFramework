package utils;


import io.qameta.allure.Step;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;
import utils.config.TestDataConfig;
import utils.service.interfaces.IBaseService;

public interface IBaseTest {
    
    @BeforeSuite(alwaysRun = true)
    void baseTestBeforeSuite();
    
    @BeforeTest(alwaysRun = true)
    void baseTestBeforeTest(ITestContext testContext);
    
    @AfterTest(alwaysRun = true)
    void baseTestAfterTest(ITestContext testContext);
    
    @BeforeMethod(alwaysRun = true)
    void baseTestBeforeMethod(ITestResult result, ITestContext testContext);
    
    @AfterMethod(alwaysRun = true)
    void baseTestAfterMethod(ITestResult result);
    
    @AfterSuite(alwaysRun = true)
    void baseTestAfterSuite();
    
    IBaseService before(String testStepDescription);
    
    IBaseService after(String testStepDescription);
    
    @Step
    IBaseService step(String testStepDescription);
    
    TestDataConfig getTestCaseData();
    
    TestDataConfig getTestCaseData(Integer instance);
    
}
