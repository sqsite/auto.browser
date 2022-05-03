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

public class MatchingTextExpectation extends Expectation {

    private static final String FIND_TEXT_DATA = "findTextData";
    private final String value;
    private final Executor executor = (Executor) ObjectSupplier.instanceOf(Executor.class);
    private boolean result;
    private boolean isMultiple;
    private boolean isVisible;
    private List<By> locatorSet;

    public MatchingTextExpectation(String value) {
        this.value = value;
    }

    @Override
    public void assertion(boolean isVisible, boolean isMultiple, List<By> locatorSet) {
        this.isMultiple = isMultiple;
        this.isVisible = isVisible;
        this.locatorSet = locatorSet;

        String duration = getWaitTime(WaitTime.EXPLICIT_WAIT_TIME) == null
                ? getWaitTime(WaitTime.DEFAULT_WAIT_TIME)
                : getWaitTime(WaitTime.EXPLICIT_WAIT_TIME);

        Duration waitDuration = Duration.ofSeconds(Integer.parseInt(duration));

        result = new FluentWait<>(executor)
                .pollingEvery(Duration.ofMillis(500))
                .forDuration(waitDuration)
                .ignoring(TimeoutException.class)
                .ignoring(StaleElementReferenceException.class)
                .withMessage(String.format("Expected value '%s' is not contained in the actual value %s", value, executor.isVisible(isVisible).withMultipleElements(isMultiple).usingLocator(locatorSet).invokeCommand(FindTextData.class, FIND_TEXT_DATA)))
                .until(e -> e
                        .withMultipleElements(isMultiple)
                        .isVisible(isVisible)
                        .usingLocator(locatorSet)
                        .invokeCommand(FindTextData.class, FIND_TEXT_DATA).toString().contains(value));
    }

    @Override
    public void orElseFail() {
        if (!result) {
            throw new ExpectationFailure(String.format("Expected value '%s' is not contained in the actual value %s", value, executor.isVisible(isVisible).withMultipleElements(isMultiple).usingLocator(locatorSet).invokeCommand(FindTextData.class, FIND_TEXT_DATA)));
        }
    }
}
