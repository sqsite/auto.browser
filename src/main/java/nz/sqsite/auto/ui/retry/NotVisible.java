package nz.sqsite.auto.ui.retry;

import nz.sqsite.auto.ui.command.Executor;
import nz.sqsite.auto.ui.commands.IsVisible;
import nz.sqsite.auto.ui.locator.LocatorMatcher;
import nz.sqsite.auto.ui.wait.ThreadSleep;
import org.openqa.selenium.By;

import java.util.LinkedList;
import java.util.List;

public class NotVisible extends RetryCondition {

    private final Executor executor = new Executor();
    private final List<By> newElementLocatorSet;

    public NotVisible(String locatorMatcher) {
        newElementLocatorSet = new LinkedList<>();
        newElementLocatorSet.add(LocatorMatcher.getMatchedLocator(locatorMatcher));
    }

    @Override
    public boolean retry(boolean isVisible, boolean isMultiple, List<By> locatorSet) {

        boolean result = executor
                .withMultipleElements(isMultiple)
                .isVisible(isVisible)
                .usingLocator(newElementLocatorSet)
                .invokeCommand(IsVisible.class);


        if (!result) {
            ThreadSleep.forMilliS(500);
            executor.invokeCommand();
        } else {
            return true;
        }

        result = executor
                .withMultipleElements(isMultiple)
                .isVisible(isVisible)
                .usingLocator(newElementLocatorSet)
                .invokeCommand(IsVisible.class);

        return result;
    }
}
