package utils.listeners;

import annotations.SkipTest;
import org.slf4j.Logger;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;
import org.testng.SkipException;
import utils.config.EnvDataConfig;
import utils.enums.Environment;

import java.lang.reflect.Method;
import java.util.Arrays;

public class SkipTestListener implements IInvokedMethodListener {
    
    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(SkipTestListener.class);
    
    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        Method testMethod = method.getTestMethod().getConstructorOrMethod().getMethod();
        SkipTest skipTest = testMethod.getAnnotation(SkipTest.class);
        if (skipTest != null) {
            Environment[] environments = skipTest.environments();
            Environment currentEnvironment = new EnvDataConfig().getEnvironment();
            
            // Skip if no environments are specified or if the current environment matches the specified environments
            boolean shouldSkip = environments.length == 0 ||
                    (currentEnvironment != null && Arrays.asList(environments).contains(currentEnvironment));
            
            if (shouldSkip) {
                String skipReason = "Skipping test: " + testMethod.getName() + " - " + skipTest.description() + " - for environment: " + currentEnvironment;
                LOGGER.info(skipReason);
                testResult.setStatus(ITestResult.SKIP);
                testResult.setThrowable(new SkipException(skipReason));
            }
        }
    }
    
    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        // No action needed after invocation
    }
}
