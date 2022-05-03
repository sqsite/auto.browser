package nz.sqsite.auto.ui.verification.conditions;

import nz.sqsite.auto.ui.command.Executor;
import nz.sqsite.auto.ui.commands.GetAttribute;
import nz.sqsite.auto.ui.data.WaitTime;
import nz.sqsite.auto.ui.supplier.ObjectSupplier;
import nz.sqsite.auto.ui.wait.FluentWait;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;

import java.time.Duration;
import java.util.List;

import static nz.sqsite.auto.ui.data.WaitTimeData.getWaitTime;

public class AttributeCondition extends Condition{

    private final String attributeType;
    private final String value;
    private final Executor executor = (Executor) ObjectSupplier.instanceOf(Executor.class);

    public AttributeCondition(String attributeType, String value) {
        this.attributeType = attributeType;
        this.value = value;
    }

    @Override
    public void verify(boolean isVisible, boolean isMultiple, List<By> locatorSet) {

        String duration = getWaitTime(WaitTime.EXPLICIT_WAIT_TIME) == null
                ? getWaitTime(WaitTime.DEFAULT_WAIT_TIME)
                : getWaitTime(WaitTime.EXPLICIT_WAIT_TIME);

        Duration waitDuration = Duration.ofSeconds(Integer.parseInt(duration));

        new FluentWait<>(executor)
                .pollingEvery(Duration.ofMillis(500))
                .forDuration(waitDuration)
                .ignoring(StaleElementReferenceException.class)
                .throwing(AssertionError.class)
                .withMessage(String.format("Failed to find attribute '%s' with value '%s'",
                        attributeType, value))
                .until(e -> e
                        .withMultipleElements(isMultiple)
                        .withAttribute(attributeType)
                        .isVisible(isVisible)
                        .usingLocator(locatorSet)
                        .invokeCommand(GetAttribute.class).equals(value));
    }
}
