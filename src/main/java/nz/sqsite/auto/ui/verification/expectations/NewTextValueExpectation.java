package nz.sqsite.auto.ui.verification.expectations;

import nz.sqsite.auto.ui.command.Executor;
import nz.sqsite.auto.ui.commands.FindTextData;
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

public class NewTextValueExpectation extends Expectation {

    private final Executor executor = (Executor) ObjectSupplier.instanceOf(Executor.class);
    String existingValue;
    private boolean result;

    @Override
    public void assertion(boolean isVisible, boolean isMultiple, List<By> locatorSet) {

        String duration = getWaitTime(WaitTime.EXPLICIT_WAIT_TIME) == null
                ? getWaitTime(WaitTime.DEFAULT_WAIT_TIME)
                : getWaitTime(WaitTime.EXPLICIT_WAIT_TIME);

        Duration waitDuration = Duration.ofSeconds(Integer.parseInt(duration));

        existingValue = executor.isVisible(isVisible).withMultipleElements(isMultiple).usingLocator(locatorSet).invokeCommand(FindTextData.class, "findTextData");

        result = new FluentWait<>(executor)
                .pollingEvery(Duration.ofMillis(500))
                .forDuration(waitDuration)
                .ignoring(TimeoutException.class)
                .ignoring(StaleElementReferenceException.class)
                .withMessage(String.format("Existing value '%s' has not changed", existingValue))
                .until(e -> !e
                        .withMultipleElements(isMultiple)
                        .isVisible(isVisible)
                        .usingLocator(locatorSet)
                        .invokeCommand(FindTextData.class, "findTextData").toString().equals(existingValue));
    }

    @Override
    public void orElseFail() {
        if (!result) {
            throw new ExpectationFailure(String.format("Existing value '%s' has not changed", existingValue));
        }
    }
}
