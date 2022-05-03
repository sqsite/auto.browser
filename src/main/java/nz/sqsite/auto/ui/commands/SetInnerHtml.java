package nz.sqsite.auto.ui.commands;

import nz.sqsite.auto.ui.command.Command;
import nz.sqsite.auto.ui.command.CommandAction;
import nz.sqsite.auto.ui.command.CommandContext;
import nz.sqsite.auto.ui.command.Commands;
import nz.sqsite.auto.ui.counter.TimeCounter;
import nz.sqsite.auto.ui.exceptions.CommandExceptions;
import nz.sqsite.auto.ui.supplier.ObjectSupplier;
import nz.sqsite.auto.ui.webelement.Element;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;

import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public final class SetInnerHtml extends CommandAction implements Command {

    private final Supplier<Map<Class<? extends Throwable>, Supplier<String>>> ignoredExceptions = this::ignoredEx;
    private final Element webElement = (Element) ObjectSupplier.instanceOf(Element.class);
    private final TimeCounter timeCounter = new TimeCounter();

    private boolean isMultiple;
    private boolean visibility;
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

    public void setInnerHtmlAction() {
        Function<WebElement, String> expectedValue = e -> e.getAttribute("innerHTML");

        WebElement element = webElement.getElement(locatorSet, visibility, isMultiple);
        ((JavascriptExecutor) ((RemoteWebElement) element).getWrappedDriver()).executeScript(String.format("arguments[0].innerHTML='%s';", inputText), element);

        if (!expectedValue.apply(element).equals(inputText)) {
            element.clear();
            throw new ElementNotInteractableException("Element Not Interactable");
        }
    }

    public void setInnerHtml() {
        timeCounter.restart();
        super.execute(Commands.InputCommands.SET_INNER_HTML.toString(), ignoredExceptions, timeCounter);
    }
}
