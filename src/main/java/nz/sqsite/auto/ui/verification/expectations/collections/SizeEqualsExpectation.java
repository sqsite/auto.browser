package nz.sqsite.auto.ui.verification.expectations.collections;

import nz.sqsite.auto.ui.command.Executor;
import nz.sqsite.auto.ui.commands.GetSize;
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

public class SizeEqualsExpectation implements Expectations {

    private static final String GET_SIZE = "getSize";
    private final Executor executor = (Executor) ObjectSupplier.instanceOf(Executor.class);
    private final int size;
    private boolean result;

    public SizeEqualsExpectation(int size) {
        this.size = size;
    }


    @Override
    public void assertion(boolean isMultiple, List<By> locatorSet) {

        String duration = getWaitTime(WaitTime.EXPLICIT_WAIT_TIME) == null
                ? getWaitTime(WaitTime.DEFAULT_WAIT_TIME)
                : getWaitTime(WaitTime.EXPLICIT_WAIT_TIME);

        Duration waitDuration = Duration.ofSeconds(Integer.parseInt(duration));

        //noinspection RedundantCast
        result = new FluentWait<>(executor)
                .pollingEvery(Duration.ofMillis(500))
                .forDuration(waitDuration)
                .ignoring(TimeoutException.class)
                .ignoring(StaleElementReferenceException.class)
                .withMessage(String.format("Expected number of elements is %d but could find [only] %d element[s]", size, (int) executor.isVisible(false).withMultipleElements(isMultiple).usingLocator(locatorSet).invokeCommand(GetSize.class, GET_SIZE)))
                .until(e -> (int) e
                        .usingLocator(locatorSet)
                        .withMultipleElements(isMultiple)
                        .invokeCommand(GetSize.class, GET_SIZE) == size);
    }

    @Override
    public void orElseFail() {
        if (!result) {
            //noinspection RedundantCast
            throw new ExpectationFailure(String.format("Expected number of elements is %d but could find [only] %d element[s]", size, (int) executor.invokeCommand(GetSize.class, GET_SIZE)));
        }
    }
}
