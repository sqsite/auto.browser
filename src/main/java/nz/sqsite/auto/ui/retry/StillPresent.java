package nz.sqsite.auto.ui.retry;

import nz.sqsite.auto.ui.command.Executor;
import nz.sqsite.auto.ui.commands.GetSize;
import nz.sqsite.auto.ui.supplier.ObjectSupplier;
import nz.sqsite.auto.ui.wait.ThreadSleep;
import org.openqa.selenium.By;

import java.util.List;

public class StillPresent extends RetryCondition {

    private final Executor executor = (Executor) ObjectSupplier.instanceOf(Executor.class);

    @Override
    public boolean retry(boolean isVisible, boolean isMultiple, List<By> locatorSet) {

        boolean result = (int) executor
                .withMultipleElements(isMultiple)
                .isVisible(isVisible)
                .usingLocator(locatorSet)
                .invokeCommand(GetSize.class, "getSize") == 0;

        if (!result) {
            ThreadSleep.forMilliS(500);
            executor.invokeCommand();
        } else {
            return true;
        }

        result = (int) executor
                .withMultipleElements(isMultiple)
                .isVisible(isVisible)
                .usingLocator(locatorSet)
                .invokeCommand(GetSize.class, "getSize") == 0;

        return result;
    }
}
