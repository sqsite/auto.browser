package nz.sqsite.auto.ui.commands;

import nz.sqsite.auto.ui.command.Command;
import nz.sqsite.auto.ui.command.CommandAction;
import nz.sqsite.auto.ui.command.CommandContext;
import nz.sqsite.auto.ui.command.Commands;
import nz.sqsite.auto.ui.counter.TimeCounter;
import nz.sqsite.auto.ui.exceptions.CommandExceptions;
import nz.sqsite.auto.ui.supplier.ObjectSupplier;
import nz.sqsite.auto.ui.utils.CheckString;
import nz.sqsite.auto.ui.webelement.Element;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
public final class FindAllTextData extends CommandAction implements Command {

    private final Supplier<Map<Class<? extends Throwable>, Supplier<String>>> ignoredExceptions = this::ignoredEx;
    private final Element webElement = (Element) ObjectSupplier.instanceOf(Element.class);
    private final TimeCounter timeCounter = new TimeCounter();

    private boolean visibility;

    @Override
    public void contextSetter(CommandContext context) {
        this.locatorSet = context.getLocatorSet();
        this.visibility = context.getVisibility();
    }

    @Override
    protected Map<Class<? extends Throwable>, Supplier<String>> ignoredEx() {
        return CommandExceptions.TypeOf.stale();
    }

    public List<String> findAllTextDataAction() {
        List<WebElement> elements = webElement.getElements(locatorSet, visibility);

        List<String> textContent = elements.stream().map(WebElement::getText).collect(Collectors.toList());

        Predicate<List<String>> contentCheck = l -> l.stream().anyMatch(e -> !CheckString.isNullOrEmpty(e));

        if (contentCheck.test(textContent)) {
            return textContent;
        }
        textContent = elements.stream().map(e -> e.getAttribute("value")).collect(Collectors.toList());

        if (contentCheck.test(textContent)) {
            return textContent;
        }
        textContent = elements.stream().map(e -> e.getAttribute("innerHTML")).collect(Collectors.toList());

        return textContent;
    }

    public List<String> findAllTextData() {
        timeCounter.restart();
        return super.execute(Commands.GetCommands.FIND_ALL_TEXT_DATA.toString(), ignoredExceptions, timeCounter);
    }
}
