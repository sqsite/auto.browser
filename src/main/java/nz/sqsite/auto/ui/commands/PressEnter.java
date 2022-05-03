package nz.sqsite.auto.ui.commands;

import nz.sqsite.auto.ui.browser.Driver;
import nz.sqsite.auto.ui.command.Command;
import nz.sqsite.auto.ui.command.CommandAction;
import nz.sqsite.auto.ui.command.CommandContext;
import nz.sqsite.auto.ui.command.Commands;
import nz.sqsite.auto.ui.counter.TimeCounter;
import nz.sqsite.auto.ui.exceptions.CommandExceptions;
import nz.sqsite.auto.ui.supplier.ObjectSupplier;
import nz.sqsite.auto.ui.wait.ActivityWaiter;
import nz.sqsite.auto.ui.wait.Wait;
import nz.sqsite.auto.ui.webelement.Element;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebElement;

import java.util.Map;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public final class PressEnter extends CommandAction implements Command {

    private final Supplier<Map<Class<? extends Throwable>, Supplier<String>>> ignoredExceptions = this::ignoredEx;
    private final Element webElement = (Element) ObjectSupplier.instanceOf(Element.class);
    private final TimeCounter timeCounter = new TimeCounter();
    private final ActivityWaiter activityWaiter = (ActivityWaiter) ObjectSupplier.instanceOf(ActivityWaiter.class);

    private boolean visibility;
    private boolean isMultiple;

    @Override
    public void contextSetter(CommandContext context) {
        this.isMultiple = context.isMultiple();
        this.locatorSet = context.getLocatorSet();
        this.visibility = context.getVisibility();
    }

    @Override
    protected Map<Class<? extends Throwable>, Supplier<String>> ignoredEx() {
        return CommandExceptions.TypeOf.stale();
    }

    public void pressEnterAction() {
        WebElement element = webElement.getElement(locatorSet, visibility, isMultiple);
        new Actions(((RemoteWebElement) element).getWrappedDriver()).sendKeys(element, Keys.ENTER).perform();
        activityWaiter.waitUntilDocReady(Driver.getDriver(), Wait.getBackgroundMaxWait());
    }

    public void pressEnter() {
        timeCounter.restart();
        super.execute(Commands.KeyCommands.PRESS_ENTER_ACTION.toString(), ignoredExceptions, timeCounter);
    }
}
