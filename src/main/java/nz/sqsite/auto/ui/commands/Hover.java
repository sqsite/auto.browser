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
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebElement;

import java.util.Map;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public final class Hover extends CommandAction implements Command {

    private final Supplier<Map<Class<? extends Throwable>, Supplier<String>>> ignoredExceptions = this::ignoredEx;
    private final Element webElement = (Element) ObjectSupplier.instanceOf(Element.class);
    private final TimeCounter timeCounter = new TimeCounter();

    private boolean visibility;
    private boolean isMultiple;
    private int secondsToHover;

    @Override
    public void contextSetter(CommandContext context) {
        this.isMultiple = context.isMultiple();
        this.secondsToHover = context.getSecondsToWait();
        this.visibility = context.getVisibility();
        this.locatorSet = context.getLocatorSet();
    }

    @Override
    public Map<Class<? extends Throwable>, Supplier<String>> ignoredEx() {
        return CommandExceptions.TypeOf.stale();
    }

    public void hoverAction() {
        WebElement element = webElement.getElement(locatorSet, visibility, isMultiple);
        new Actions(((RemoteWebElement) element).getWrappedDriver()).moveToElement(element).pause(secondsToHover).perform();
    }

    public void hover() {
        timeCounter.restart();
        super.execute(Commands.MoveCommands.HOVER.toString(), ignoredExceptions, timeCounter);
    }
}
