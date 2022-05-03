package nz.sqsite.auto.ui.webelement;

import nz.sqsite.auto.ui.locator.LocatorMatcher;
import org.openqa.selenium.By;

public class ElementFinder {

    // Class not to be instantiated.
    private ElementFinder() {
    }

    /**
     * Sets the locator to be found for ui actions
     * Warning: This method is just a collector and no element finding would happen unless an action method is called
     *
     * @param locatorMatcher is a string to be passed that would be parsed into a By locator
     * @return Instance of the UIElement
     */
    public static UIElement find(String locatorMatcher) {
        return find(LocatorMatcher.getMatchedLocator(locatorMatcher));
    }

    private static UIElement find(By byLocator) {
        return new UIActions().setProperties(byLocator).withDefaultWait();
    }
}
