package nz.sqsite.auto.ui.retry;

import nz.sqsite.auto.ui.command.Executor;
import nz.sqsite.auto.ui.commands.GetSize;
import nz.sqsite.auto.ui.locator.LocatorMatcher;
import nz.sqsite.auto.ui.wait.ThreadSleep;
import org.openqa.selenium.By;

import java.util.LinkedList;
import java.util.List;

public class NotPresent extends RetryCondition {

    private final Executor executor = new Executor();
    private final List<By> newElementLocatorSet;

    public NotPresent(String locatorMatcher) {
        newElementLocatorSet = new LinkedList<>();
        newElementLocatorSet.add(LocatorMatcher.getMatchedLocator(locatorMatcher));
    }

    @Override
    public boolean retry(boolean isVisible, boolean isMultiple, List<By> locatorSet) {

        boolean result = (int) executor
                .withMultipleElements(isMultiple)
                .isVisible(isVisible)
                .usingLocator(newElementLocatorSet)
                .invokeCommand(GetSize.class) > 0;

        if (!result) {
            ThreadSleep.forMilliS(500);
            executor.invokeCommand();
        } else {
            return true;
        }

        result = (int) executor
                .withMultipleElements(isMultiple)
                .isVisible(isVisible)
                .usingLocator(newElementLocatorSet)
                .invokeCommand(GetSize.class) > 0;

        return result;
    }
}
