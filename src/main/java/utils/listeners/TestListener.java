package utils.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

public class TestListener extends TestListenerAdapter {
    
    private static final String LOGGER_SEPARATOR = "====================================================";
    private static final Logger log = LoggerFactory.getLogger(TestListener.class);
    
    @Override
    public void onFinish(ITestContext context) {
        log.info(LOGGER_SEPARATOR);
        log.info("Finished running suite {}", context.getName());
        log.info("Total tests: {}", context.getAllTestMethods().length);
        log.info("Total Passed: {}", context.getPassedTests().size());
        log.info("Total failures: {}", context.getFailedTests().size());
        log.info("Total Skipped: {}", context.getSkippedTests().size());
        log.info(LOGGER_SEPARATOR);
    }
    
    @Override
    public void onStart(ITestContext context) {
        log.info(LOGGER_SEPARATOR);
        log.info("Started running suite {}", context.getName());
        log.info(LOGGER_SEPARATOR);
    }
    
    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult tr) {
        log.info(LOGGER_SEPARATOR);
        log.warn("Test partially succeeded: {}", tr.getName());
        log.info(LOGGER_SEPARATOR);
    }
    
    @Override
    public void onTestFailure(ITestResult tr) {
        log.info(LOGGER_SEPARATOR);
        log.error("Test failed: {}", tr.getName(), tr.getThrowable());
        log.info(LOGGER_SEPARATOR);
    }
    
    @Override
    public void onTestSkipped(ITestResult tr) {
        log.info(LOGGER_SEPARATOR);
        log.warn("Test skipped: {}", tr.getName());
        log.info(LOGGER_SEPARATOR);
    }
    
    @Override
    public void onTestStart(ITestResult tr) {
        log.info(LOGGER_SEPARATOR);
        log.info("Test Name: {}", tr.getName());
        log.info(LOGGER_SEPARATOR);
    }
    
    @Override
    public void onTestSuccess(ITestResult tr) {
        log.info(LOGGER_SEPARATOR);
        log.info("Test succeeded: {}", tr.getName());
        log.info(LOGGER_SEPARATOR);
    }
}
