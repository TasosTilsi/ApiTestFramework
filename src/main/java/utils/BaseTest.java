package utils;


import com.google.common.collect.ImmutableMap;
import io.qameta.allure.Step;
import org.slf4j.Logger;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;
import utils.config.EnvDataConfig;
import utils.config.ResourcesConfig;
import utils.config.TestDataConfig;
import utils.service.implementation.WebService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.github.automatedowl.tools.AllureEnvironmentWriter.allureEnvironmentWriter;

public class BaseTest {
    
    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(BaseTest.class);
    private WebService webService;
    
    private EnvDataConfig envDataConfig = new EnvDataConfig();
    
    @BeforeSuite(alwaysRun = true)
    public void baseTestBeforeSuite() {
        
        System.setProperty("org.freemarker.loggerLibrary", "none");
        try {
            Files.deleteIfExists(
                    Paths.get(new ResourcesConfig().getTargetPath() + "test.json"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        setAllureEnvironment();
    }
    
    @BeforeTest(alwaysRun = true)
    public void baseTestBeforeTest(ITestContext testContext) {
        webService = new WebService();
    }
    
    @AfterTest(alwaysRun = true)
    public void baseTestAfterTest(ITestContext testContext) {
        after("BaseTest After Test");
    }
    
    @BeforeMethod(alwaysRun = true)
    public void baseTestBeforeMethod(ITestContext testContext) {
        before("BaseTest Before Method");
    }
    
    @AfterMethod(alwaysRun = true)
    public void baseTestAfterMethod(ITestResult result) throws IOException {
        after("BaseTest After Method");
    }
    
    @AfterSuite(alwaysRun = true)
    public void baseTestAfterSuite() {
        after("BaseTest After Suite");
    }
    
    public WebService before(String testStepDescription) {
        logger.info("BEFORE TEST: " + testStepDescription);
        return webService;
    }
    
    public WebService after(String testStepDescription) {
        logger.info("AFTER TEST: " + testStepDescription);
        return webService;
    }
    
    @Step
    public WebService step(String testStepDescription) {
        logger.info("TEST STEP: " + testStepDescription);
        webService.context().setStepDescription(testStepDescription);
        return webService;
    }
    
    public TestDataConfig getTestCaseData() {
        return getTestCaseData(null);
    }
    
    public TestDataConfig getTestCaseData(Integer instance) {
        return instance == null ? new TestDataConfig(getTestSuiteName(getFullPath()), getTestCaseName(getFullPath())) :
                new TestDataConfig(getTestSuiteName(getFullPath()), getTestCaseName(getFullPath()), instance);
    }
    
    private String getFullPath() {
        return getClass().getCanonicalName();
    }
    
    private String getTestCaseName(String classPath) {
        String testCase = null;
        for (String packageName : classPath.split("\\.")) {
            if (packageName.startsWith("TC_")) {
                testCase = packageName;
                break;
            }
        }
        Assert.assertNotNull(testCase, "No Test Case found in provided path: " + classPath);
        return testCase;
    }
    
    private String getTestSuiteName(String classPath) {
        String testSuite = null;
        for (String packageName : classPath.split("\\.")) {
            if (packageName.startsWith("TS_")) {
                testSuite = packageName;
                break;
            }
        }
        Assert.assertNotNull(testSuite, "No Test Suite found in provided path: " + classPath);
        return testSuite;
    }
    
    
    /**
     * Sets the Allure environment for the test executions.
     */
    private void setAllureEnvironment() {
        
        String allureResultsPath = "test-results/allure-results/";
        File allureResultsDirectory = new File(allureResultsPath);
        if (!allureResultsDirectory.exists()) {
            allureResultsDirectory.mkdirs();
        }
        File environmentXmlFile = new File(allureResultsPath + "environment.xml");
        if (!environmentXmlFile.exists()) {
            ImmutableMap<String, String> environmentMap = createEnvironmentMap();
            allureEnvironmentWriter(
                    environmentMap, allureResultsPath
            );
        }
    }
    
    private ImmutableMap<String, String> createEnvironmentMap() {
        String mavenHome = "MAVEN_HOME path is missing";
        if (System.getenv("MAVEN_HOME") != null) {
            mavenHome = System.getenv("MAVEN_HOME");
        }
        return ImmutableMap.<String, String>builder()
                .put("FRAMEWORK", "API Assurance Automation")
                .put("OS", System.getProperty("os.name"))
                .put("JAVA", System.getProperty("java.home"))
                .put("MAVEN", mavenHome)
                .put("ENVIRONMENT", envDataConfig.getEnvironment().getName())
                .put("URL", envDataConfig.getRestApiUrl())
//                .put("PROJECT_KEY", envDataConfig.getJiraTestPlan().split("-")[0])
//                .put("TEST_PLAN_ID", envDataConfig.getJiraTestPlan())
//                .put("CYCLE_NAME", envDataConfig.getJiraTestCycle())
//                .put("JIRA_TOKEN", envDataConfig.getJiraAccessToken())
//                .put("CREATE_BUGS", envDataConfig.getJiraCreateBugs())
//                .put("JIRA_UPDATE", envDataConfig.getJiraPublish())
                .build();
    }
    
}
