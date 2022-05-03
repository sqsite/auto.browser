package nz.sqsite.auto.ui.retry;

import nz.sqsite.auto.ui.command.Executor;
import nz.sqsite.auto.ui.commands.IsVisible;
import nz.sqsite.auto.ui.supplier.ObjectSupplier;
import nz.sqsite.auto.ui.wait.ThreadSleep;
import org.openqa.selenium.By;

import java.util.List;

public class StillVisible extends RetryCondition {

    private final Executor executor = (Executor) ObjectSupplier.instanceOf(Executor.class);

    @Override
    public boolean retry(boolean isVisible, boolean isMultiple, List<By> locatorSet) {


        boolean result = (executor
                .withMultipleElements(isMultiple)
                .isVisible(isVisible)
                .usingLocator(locatorSet)
                .invokeCommand(IsVisible.class, "isVisible"));

        if (result) {
            ThreadSleep.forMilliS(500);
            executor.invokeCommand();
        } else {
            return true;
        }

        result = !(boolean) (executor
                .withMultipleElements(isMultiple)
                .isVisible(isVisible)
                .usingLocator(locatorSet)
                .invokeCommand(IsVisible.class, "isVisible"));

        return result;
    }
}
