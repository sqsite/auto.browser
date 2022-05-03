package nz.sqsite.auto.ui.commands;

import nz.sqsite.auto.ui.command.Command;
import nz.sqsite.auto.ui.command.CommandAction;
import nz.sqsite.auto.ui.command.CommandContext;
import nz.sqsite.auto.ui.command.Commands;
import nz.sqsite.auto.ui.counter.TimeCounter;
import nz.sqsite.auto.ui.exceptions.CommandExceptions;
import nz.sqsite.auto.ui.supplier.ObjectSupplier;
import nz.sqsite.auto.ui.webelement.Element;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public final class ClearAndType extends CommandAction implements Command {

    private final Supplier<Map<Class<? extends Throwable>, Supplier<String>>> ignoredExceptions = this::ignoredEx;
    private final Element webElement = (Element) ObjectSupplier.instanceOf(Element.class);
    private final TimeCounter timeCounter = new TimeCounter();

    private CharSequence[] charSequences;
    private boolean visibility;
    private boolean isMultiple;

    @Override
    public void contextSetter(CommandContext context) {
        this.charSequences = context.getSequence();
        this.locatorSet = context.getLocatorSet();
        this.visibility = context.getVisibility();
        this.isMultiple = context.isMultiple();
    }


    @Override
    public Map<Class<? extends Throwable>, Supplier<String>> ignoredEx() {
        return CommandExceptions.Of.clear();
    }

    public void clearAndTypeAction() {
        Function<WebElement, String> expectedValue = e -> e.getAttribute("value");

        WebElement element = webElement.getElement(locatorSet, visibility, isMultiple);
        int existingCharsLength = expectedValue.apply(element).length();

        for (int i = 0; i < existingCharsLength; i++) {
            element.sendKeys(Keys.BACK_SPACE);
        }

        for (CharSequence c : charSequences) {
            element.sendKeys(c);
        }
    }

    public void clearAndType() {
        timeCounter.restart();
        super.execute(Commands.ClickCommands.CLEAR_AND_TYPE.toString(), ignoredExceptions, timeCounter);
    }
}
