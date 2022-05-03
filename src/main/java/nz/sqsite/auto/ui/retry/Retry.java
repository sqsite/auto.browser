package nz.sqsite.auto.ui.retry;

import nz.sqsite.auto.ui.wait.ThreadSleep;
import org.openqa.selenium.By;

import java.util.List;

public class Retry {

    private Retry() {
    }

    public static void retry(boolean isVisible, boolean isMultiple, List<By> locatorSet, RetryCondition retryCondition, int numberOfTimes) {
        for (int i = 0; i < numberOfTimes; i++) {
            if (retryCondition.retry(isVisible, isMultiple, locatorSet)) {
                break;
            }
            ThreadSleep.forMilliS(500);
        }
    }
}
