package nz.sqsite.auto.ui.commands;

import nz.sqsite.auto.ui.command.Command;
import nz.sqsite.auto.ui.command.CommandAction;
import nz.sqsite.auto.ui.command.CommandContext;
import nz.sqsite.auto.ui.command.Commands;
import nz.sqsite.auto.ui.counter.TimeCounter;
import nz.sqsite.auto.ui.exceptions.CommandExceptions;
import nz.sqsite.auto.ui.supplier.ObjectSupplier;
import nz.sqsite.auto.ui.webelement.Element;
import org.openqa.selenium.WebElement;

import java.util.Map;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public final class SendKeys extends CommandAction implements Command {

    private final Supplier<Map<Class<? extends Throwable>, Supplier<String>>> ignoredExceptions = this::ignoredEx;
    private final Element webElement = (Element) ObjectSupplier.instanceOf(Element.class);
    private final TimeCounter timeCounter = new TimeCounter();

    private boolean isMultiple;
    private boolean visibility;
    private CharSequence[] charSequences;

    @Override
    public void contextSetter(CommandContext context) {
        this.charSequences = context.getSequence();
        this.locatorSet = context.getLocatorSet();
        this.isMultiple = context.isMultiple();
        this.visibility = context.getVisibility();
    }

    @Override
    public Map<Class<? extends Throwable>, Supplier<String>> ignoredEx() {
        return CommandExceptions.Of.sendKeys();
    }

    public void sendKeysAction() {
        WebElement element = webElement.getElement(locatorSet, visibility, isMultiple);
        element.sendKeys(charSequences);
    }

    public void sendKeys() {
        timeCounter.restart();
        super.execute(Commands.InputCommands.SEND_KEYS.toString(), ignoredExceptions, timeCounter);
    }
}
