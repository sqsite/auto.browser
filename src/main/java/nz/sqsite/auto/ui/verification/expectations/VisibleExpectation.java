package nz.sqsite.auto.ui.verification.expectations;

import nz.sqsite.auto.ui.command.Executor;
import nz.sqsite.auto.ui.commands.IsVisible;
import nz.sqsite.auto.ui.data.WaitTime;
import nz.sqsite.auto.ui.exceptions.ExpectationFailure;
import nz.sqsite.auto.ui.exceptions.TimeoutException;
import nz.sqsite.auto.ui.supplier.ObjectSupplier;
import nz.sqsite.auto.ui.wait.FluentWait;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;

import java.time.Duration;
import java.util.List;

import static nz.sqsite.auto.ui.data.WaitTimeData.getWaitTime;

public class VisibleExpectation extends Expectation {

    private final Executor executor = (Executor) ObjectSupplier.instanceOf(Executor.class);
    private boolean result;
    private By byLocator;

    @Override
    public void assertion(boolean isVisible, boolean isMultiple, List<By> locatorSet) {
        byLocator = locatorSet.get(0);

        String duration = getWaitTime(WaitTime.EXPLICIT_WAIT_TIME) == null
                ? getWaitTime(WaitTime.DEFAULT_WAIT_TIME)
                : getWaitTime(WaitTime.EXPLICIT_WAIT_TIME);

        Duration waitDuration = Duration.ofSeconds(Integer.parseInt(duration));

        result = new FluentWait<>(executor)
                .pollingEvery(Duration.ofMillis(500))
                .forDuration(waitDuration)
                .ignoring(TimeoutException.class)
                .ignoring(StaleElementReferenceException.class)
                .withMessage(String.format("Element %s expected to be visible but was not", locatorSet.get(0)))
                .until(e -> e
                        .withMultipleElements(isMultiple)
                        .isVisible(isVisible)
                        .usingLocator(locatorSet)
                        .invokeCommand(IsVisible.class, "isVisible"));
    }

    @Override
    public void orElseFail() {
        if (!result) {
            throw new ExpectationFailure(String.format("Expected condition failed : Element %s expected to be visible but was not", byLocator));
        }
    }

}
