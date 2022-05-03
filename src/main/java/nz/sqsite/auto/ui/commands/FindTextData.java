package nz.sqsite.auto.ui.commands;

import nz.sqsite.auto.ui.command.Command;
import nz.sqsite.auto.ui.command.CommandAction;
import nz.sqsite.auto.ui.command.CommandContext;
import nz.sqsite.auto.ui.command.Commands;
import nz.sqsite.auto.ui.counter.TimeCounter;
import nz.sqsite.auto.ui.exceptions.CommandExceptions;
import nz.sqsite.auto.ui.supplier.ObjectSupplier;
import nz.sqsite.auto.ui.webelement.Element;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;

import java.util.Map;
import java.util.function.Supplier;

import static com.google.common.base.Strings.isNullOrEmpty;

@SuppressWarnings("unused")
public final class FindTextData extends CommandAction implements Command {

    private final Supplier<Map<Class<? extends Throwable>, Supplier<String>>> ignoredExceptions = this::ignoredEx;
    private final Element webElement = (Element) ObjectSupplier.instanceOf(Element.class);
    private final TimeCounter timeCounter = new TimeCounter();

    private boolean isMultiple;
    private boolean visibility;

    @Override
    public void contextSetter(CommandContext context) {
        this.isMultiple = context.isMultiple();
        this.visibility = context.getVisibility();
        this.locatorSet = context.getLocatorSet();
    }

    @Override
    public Map<Class<? extends Throwable>, Supplier<String>> ignoredEx() {
        return CommandExceptions.TypeOf.stale();
    }

    public String findTextDataAction() {
        String textContent;

        WebElement element = webElement.getElement(locatorSet, visibility, isMultiple);
        textContent = element.getText();

        if (!isNullOrEmpty(textContent)) {
            return textContent;
        }
        textContent = element.getAttribute("value");

        if (!isNullOrEmpty(textContent)) {
            return textContent;
        }
        textContent = element.getAttribute("innerHTML");

        if (!isNullOrEmpty(textContent)) {
            return textContent;
        }
        textContent = (String) ((JavascriptExecutor) ((RemoteWebElement) element).getWrappedDriver()).executeScript("return arguments[0].innerHTML;", element);

        if (!isNullOrEmpty(textContent)) {
            return textContent;
        }
        return "";
    }

    public String findTextData() {
        timeCounter.restart();
        return super.execute(Commands.GetCommands.FIND_TEXT_DATA.toString(), ignoredExceptions, timeCounter);
    }
}