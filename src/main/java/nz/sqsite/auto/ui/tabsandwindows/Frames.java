package nz.sqsite.auto.ui.tabsandwindows;

import nz.sqsite.auto.ui.wait.Wait;
import nz.sqsite.auto.ui.webelement.Element;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static nz.sqsite.auto.ui.locator.LocatorMatcher.getMatchedLocator;

public class Frames {
    private Frames() {
    }

    public static void switchToFrame(String locator) {
        switchToFrame(locator, false);
    }

    public static void switchToFrame(String locator, boolean frameVisibility) {
        WebElement iframeElement = Element.element(getMatchedLocator(locator), frameVisibility);
        ExpectedCondition<WebDriver> availableFrame = ExpectedConditions.frameToBeAvailableAndSwitchToIt(iframeElement);
        Wait.getWait().until(availableFrame);
    }
}
