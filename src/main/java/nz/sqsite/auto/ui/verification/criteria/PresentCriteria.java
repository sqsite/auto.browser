package nz.sqsite.auto.ui.verification.criteria;

import nz.sqsite.auto.ui.command.Executor;
import nz.sqsite.auto.ui.commands.GetSize;
import nz.sqsite.auto.ui.data.WaitTime;
import nz.sqsite.auto.ui.supplier.ObjectSupplier;
import nz.sqsite.auto.ui.wait.FluentWait;
import org.openqa.selenium.By;

import java.time.Duration;
import java.util.List;

import static nz.sqsite.auto.ui.data.WaitTimeData.getWaitTime;

public class PresentCriteria extends Criteria {

    private final Executor executor = (Executor) ObjectSupplier.instanceOf(Executor.class);

    @Override
    public void verify(boolean isVisible, boolean isMultiple, List<By> locatorSet) {

        String duration = getWaitTime(WaitTime.EXPLICIT_WAIT_TIME) == null
                ? getWaitTime(WaitTime.DEFAULT_WAIT_TIME)
                : getWaitTime(WaitTime.EXPLICIT_WAIT_TIME);

        Duration waitDuration = Duration.ofSeconds(Integer.parseInt(duration));

        new FluentWait<>(executor)
                .pollingEvery(Duration.ofMillis(500))
                .forDuration(waitDuration)
                .withMessage(String.format("No element is found with %s", locatorSet.get(0)))
                .until(e -> (int) e
                        .withMultipleElements(isMultiple)
                        .isVisible(isVisible)
                        .usingLocator(locatorSet)
                        .invokeCommand(GetSize.class, "getSize") > 0);
    }
}
