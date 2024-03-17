package utils.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Wait {
    
    private static final Logger logger = LoggerFactory.getLogger(Wait.class);
    
    public void waitForXSeconds(int seconds) {
        logger.warn("Wait for {} seconds", seconds);
        waitFor(seconds);
    }
    
    private void waitFor(int seconds) {
        int milliseconds = seconds * 1000;
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            logger.warn("Interrupted!", e);
            Thread.currentThread().interrupt();
        }
    }
    
    public void waitForXMinutes(int minutes) {
        int seconds = minutes * 60;
        logger.warn("Wait for {} minutes", minutes);
        waitFor(seconds);
    }
    
}
