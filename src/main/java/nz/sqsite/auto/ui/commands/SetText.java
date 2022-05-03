package nz.sqsite.auto.ui.commands;

import nz.sqsite.auto.ui.command.Command;
import nz.sqsite.auto.ui.command.CommandAction;
import nz.sqsite.auto.ui.command.CommandContext;
import nz.sqsite.auto.ui.command.Commands;
import nz.sqsite.auto.ui.counter.TimeCounter;
import nz.sqsite.auto.ui.exceptions.CommandExceptions;
import nz.sqsite.auto.ui.exceptions.SetTextException;
import nz.sqsite.auto.ui.supplier.ObjectSupplier;
import nz.sqsite.auto.ui.webelement.Element;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public final class SetText extends CommandAction implements Command {

    private final Supplier<Map<Class<? extends Throwable>, Supplier<String>>> ignoredExceptions = this::ignoredEx;
    private final Element webElement = (Element) ObjectSupplier.instanceOf(Element.class);

    private boolean isMultiple;
    private boolean visibility;
    private TimeCounter timeCounter;
    private String inputText;

    @Override
    public void contextSetter(CommandContext context) {
        this.inputText = context.getTextInput();
        this.locatorSet = context.getLocatorSet();
        this.visibility = context.getVisibility();
        this.isMultiple = context.isMultiple();
    }

    @Override
    public Map<Class<? extends Throwable>, Supplier<String>> ignoredEx() {
        return CommandExceptions.Of.sendKeys();
    }

    public void setTextAction() {
        if (timeCounter == null) {
            timeCounter = new TimeCounter();
        }

        Function<WebElement, String> currentInputValue = e -> e.getAttribute("value");

        WebElement element = webElement.getElement(locatorSet, visibility, isMultiple);
        if (!currentInputValue.apply(element).equals(inputText)) {
            element.sendKeys(inputText);
        }
        if (timeCounter.timeElapsed(Duration.ofSeconds(2))) {
            int existingCharsLength = currentInputValue.apply(element).length();
            for (int i = 0; i < existingCharsLength; i++) {
                element.sendKeys(Keys.BACK_SPACE);
            }
            element.sendKeys(inputText);
        }
        if (!currentInputValue.apply(element).equals(inputText)) {
            element.clear();
            throw new SetTextException("Error when tried to input text using setText method");
        }
        timeCounter = null;
    }

    public void setText() {
        timeCounter = new TimeCounter();
        super.execute(Commands.InputCommands.SET_TEXT.toString(), ignoredExceptions, timeCounter);
    }
}
