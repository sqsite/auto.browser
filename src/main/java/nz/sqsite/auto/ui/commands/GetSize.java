package nz.sqsite.auto.ui.commands;

import nz.sqsite.auto.ui.command.Command;
import nz.sqsite.auto.ui.command.CommandAction;
import nz.sqsite.auto.ui.command.CommandContext;
import nz.sqsite.auto.ui.command.Commands;
import nz.sqsite.auto.ui.counter.TimeCounter;
import nz.sqsite.auto.ui.exceptions.CommandExceptions;
import nz.sqsite.auto.ui.supplier.ObjectSupplier;
import nz.sqsite.auto.ui.webelement.Element;

import java.util.Map;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public final class GetSize extends CommandAction implements Command {

    private final Supplier<Map<Class<? extends Throwable>, Supplier<String>>> ignoredExceptions = this::ignoredEx;
    private final Element webElement = (Element) ObjectSupplier.instanceOf(Element.class);
    private final TimeCounter timeCounter = new TimeCounter();

    @Override
    public void contextSetter(CommandContext context) {
        this.locatorSet = context.getLocatorSet();
    }

    @Override
    protected Map<Class<? extends Throwable>, Supplier<String>> ignoredEx() {
        return CommandExceptions.TypeOf.stale();
    }

    public int getSizeAction() {
        return webElement.getElements(locatorSet).size();
    }

    public int getSize() {
        timeCounter.restart();
        return super.execute(Commands.GetCommands.GET_SIZE.toString(), ignoredExceptions, timeCounter);
    }
}
