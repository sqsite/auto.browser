package nz.sqsite.auto.ui.commands;

import nz.sqsite.auto.ui.command.Command;
import nz.sqsite.auto.ui.command.CommandAction;
import nz.sqsite.auto.ui.command.CommandContext;
import nz.sqsite.auto.ui.command.Commands;
import nz.sqsite.auto.ui.counter.TimeCounter;
import nz.sqsite.auto.ui.exceptions.CommandExceptions;
import nz.sqsite.auto.ui.supplier.ObjectSupplier;
import nz.sqsite.auto.ui.webelement.Element;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;

import java.util.Map;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public final class GetDimension extends CommandAction implements Command {

    private final Supplier<Map<Class<? extends Throwable>, Supplier<String>>> ignoredExceptions = this::ignoredEx;
    private final Element webElement = (Element) ObjectSupplier.instanceOf(Element.class);
    private final TimeCounter timeCounter = new TimeCounter();

    private boolean visibility;
    private boolean isMultiple;

    @Override
    public void contextSetter(CommandContext context) {
        this.isMultiple = context.isMultiple();
        this.visibility = context.getVisibility();
        this.locatorSet = context.getLocatorSet();
    }

    @Override
    protected Map<Class<? extends Throwable>, Supplier<String>> ignoredEx() {
        return CommandExceptions.TypeOf.stale();
    }

    public Dimension getDimensionAction() {
        WebElement element = webElement.getElement(locatorSet, visibility, isMultiple);
        return element.getSize();
    }

    public Dimension getDimension() {
        timeCounter.restart();
        return super.execute(Commands.GetCommands.GET_DIMENSION.toString(), ignoredExceptions, timeCounter);
    }
}
