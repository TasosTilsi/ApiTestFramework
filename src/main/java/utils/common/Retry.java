package utils.common;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import utils.config.EnvDataConfig;

public class Retry implements IRetryAnalyzer {
    
    private static final int maxRetryCount = new EnvDataConfig().getRetry();
    private int retryCount = 1;
    
    /**
     * A function that retries the test if it fails.
     *
     * @param iTestResult the result of the test
     * @return true if the test should be retried, false otherwise
     */
    public boolean retry(ITestResult iTestResult) {
        if (!iTestResult.isSuccess()) {
            if (retryCount < maxRetryCount) {
                retryCount++;
                iTestResult.setStatus(ITestResult.FAILURE);
                return true;
            }
        } else {
            iTestResult.setStatus(ITestResult.SUCCESS);
        }
        return false;
    }
}
