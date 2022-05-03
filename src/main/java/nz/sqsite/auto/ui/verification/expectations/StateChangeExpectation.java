package nz.sqsite.auto.ui.verification.expectations;

import nz.sqsite.auto.ui.command.Executor;
import nz.sqsite.auto.ui.commands.FindTextData;
import nz.sqsite.auto.ui.commands.GetAllAttributes;
import nz.sqsite.auto.ui.commands.GetAllCssAttributes;
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

public class StateChangeExpectation extends Expectation {

    private final Executor executor = (Executor) ObjectSupplier.instanceOf(Executor.class);
    private boolean result;


    @Override
    public void assertion(boolean isVisible, boolean isMultiple, List<By> locatorSet) {

        String duration = getWaitTime(WaitTime.EXPLICIT_WAIT_TIME) == null
                ? getWaitTime(WaitTime.DEFAULT_WAIT_TIME)
                : getWaitTime(WaitTime.EXPLICIT_WAIT_TIME);

        Duration waitDuration = Duration.ofSeconds(Integer.parseInt(duration));

        String text = executor.isVisible(isVisible).withMultipleElements(isMultiple).usingLocator(locatorSet).invokeCommand(FindTextData.class, "findTextData").toString();
        String cssProperties = executor.isVisible(isVisible).withMultipleElements(isMultiple).usingLocator(locatorSet).invokeCommand(GetAllCssAttributes.class, "getAllCssAttributes").toString();
        String attributes = executor.isVisible(isVisible).withMultipleElements(isMultiple).usingLocator(locatorSet).invokeCommand(GetAllAttributes.class, "getAllAttributes").toString();

        result = new FluentWait<>(executor)
                .pollingEvery(Duration.ofMillis(500))
                .forDuration(waitDuration)
                .ignoring(TimeoutException.class)
                .ignoring(StaleElementReferenceException.class)
                .withMessage("Expected a change of state but did not happen")
                .until(e ->
                        (!e
                                .withMultipleElements(isMultiple)
                                .isVisible(isVisible)
                                .usingLocator(locatorSet)
                                .invokeCommand(FindTextData.class, "findTextData").toString().equals(text))
                                || !e
                                .withMultipleElements(isMultiple)
                                .isVisible(isVisible)
                                .usingLocator(locatorSet)
                                .invokeCommand(GetAllCssAttributes.class, "getAllCssAttributes").toString().equals(cssProperties)
                                || !e
                                .withMultipleElements(isMultiple)
                                .isVisible(isVisible)
                                .usingLocator(locatorSet)
                                .invokeCommand(GetAllAttributes.class, "getAllAttributes").toString().equals(attributes));
    }

    @Override
    public void orElseFail() {
        if (!result) {
            throw new ExpectationFailure("Expected a change of state but did not happen");
        }
    }
}
