package nz.sqsite.auto.ui.verification.conditions.collections;

import nz.sqsite.auto.ui.command.Executor;
import nz.sqsite.auto.ui.commands.GetSize;
import nz.sqsite.auto.ui.supplier.ObjectSupplier;
import nz.sqsite.auto.ui.wait.FluentWait;
import org.openqa.selenium.By;

import java.time.Duration;
import java.util.List;


/*
 * Wait duration is hard coded as not to double up the wait time because find.elements will wait for the explicit wait time
 */
public class CollectionSize extends CollectionsCondition {
    private final int value;
    private final Executor executor = (Executor) ObjectSupplier.instanceOf(Executor.class);

    public CollectionSize(int value) {
        this.value = value;
    }

    @Override
    public void verify(boolean isVisible, boolean isMultiple, List<By> locatorSet) {

        Duration waitDuration = Duration.ofSeconds(1);

        //noinspection RedundantCast
        new FluentWait<>(executor)
                .pollingEvery(Duration.ofMillis(500))
                .forDuration(waitDuration)
                .withMessage(String.format("Expected number of elements is %d but could find %d element[s]", value, (int) executor.isVisible(isVisible).withMultipleElements(isMultiple).usingLocator(locatorSet).invokeCommand(GetSize.class, "getSize")))
                .until(e -> (int) e
                        .isVisible(isVisible)
                        .withMultipleElements(isMultiple)
                        .usingLocator(locatorSet)
                        .invokeCommand(GetSize.class) == value);
    }
}
