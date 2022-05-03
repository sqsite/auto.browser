package nz.sqsite.auto.ui.webelement;

import nz.sqsite.auto.ui.counter.TimeCounter;
import nz.sqsite.auto.ui.data.ElementData;
import nz.sqsite.auto.ui.data.WaitTime;
import nz.sqsite.auto.ui.exceptions.ContextException;
import nz.sqsite.auto.ui.wait.ThreadSleep;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static nz.sqsite.auto.ui.data.GlobalData.getData;
import static nz.sqsite.auto.ui.data.WaitTimeData.getWaitTime;


public class Element {

    private static final Logger logger = LogManager.getLogger(Element.class);

    private TimeCounter timeCounter;

    public static WebElement element(By byLocator) {
        return element(byLocator, true);
    }

    public static WebElement element(By byLocator, boolean value) {
        return new FindWebElement().webElement(byLocator, value);
    }


    public WebElement getElement(List<By> locatorSet, boolean visibility, boolean isMultiple) {
        final int duration = Integer.parseInt(getWaitTime(WaitTime.EXPLICIT_WAIT_TIME) == null ? getWaitTime(WaitTime.DEFAULT_WAIT_TIME) : getWaitTime(WaitTime.EXPLICIT_WAIT_TIME));

        if (timeCounter == null) {
            timeCounter = new TimeCounter();
        }

        WebElement element;
        try {
            if (locatorSet.size() > 1) {
                List<By> locators = new ArrayList<>(locatorSet);
                element = new FindWebElement().webElement(locators.get(0), visibility);

                for (int i = 1; i < locators.size() - 1; i++) {
                    element = element.findElement(locators.get(i));
                }
                if (isMultiple) {
                    element = element.findElements(locators.get(locators.size() - 1)).get(Integer.parseInt(getData(ElementData.INDEX)));
                } else {
                    element = element.findElement(locators.get(locators.size() - 1));
                }
            } else if (isMultiple) {
                element = new FindWebElement().webElements(locatorSet.get(0), Integer.parseInt(getData(ElementData.INDEX)), visibility);
            } else if (locatorSet.size() == 1) {
                element = new FindWebElement().webElement(locatorSet.get(0), visibility);
            } else {
                throw new ContextException("Locator(s) not set. Set locator context with appropriate function");
            }

            if (!timeCounter.timeElapsed(Duration.ofSeconds(duration))) {
                Point one = element.getLocation();
                ThreadSleep.forMilliS(100);
                Point two = element.getLocation();

                int xCordDiff = one.getX() - two.getX();
                int yCordDiff = one.getY() - two.getY();

                if (xCordDiff != 0 || yCordDiff != 0) {
                    logger.info("Element not stable, movement detected, retrying to find the element");
                    element = getElement(locatorSet, visibility, isMultiple);
                }
            }

            timeCounter = null;
        } catch (WebDriverException e) {
            logger.error(e.getMessage());
            ThreadSleep.forMilliS(500);
            if (timeCounter.timeElapsed(Duration.ofSeconds(duration))) {
                throw e;
            }
            element = getElement(locatorSet, visibility, isMultiple);
        }

        return element;
    }


    public List<WebElement> getElements(List<By> locatorSet, boolean visibility) {

        int duration = Integer.parseInt(getWaitTime(WaitTime.DEFAULT_WAIT_TIME));

        if (timeCounter == null) {
            timeCounter = new TimeCounter();
        }
        List<WebElement> elements;
        WebElement element;

        try {
            if (locatorSet.size() > 1) {
                List<By> locators = new ArrayList<>(locatorSet);
                element = new FindWebElement().webElement(locators.get(0), visibility);

                for (int i = 1; i < locators.size() - 1; i++) {
                    element = element.findElement(locators.get(i));
                }
                elements = element.findElements(locators.get(locators.size() - 1));
            } else {
                elements = new FindWebElement().webElements(locatorSet.get(0), visibility);
            }
        } catch (WebDriverException e) {
            logger.error(e.getMessage());

            ThreadSleep.forMilliS(500);
            if (timeCounter.timeElapsed(Duration.ofSeconds(duration))) {
                throw e;
            }
            elements = getElements(locatorSet, visibility);
        }

        timeCounter = null;
        return elements;
    }

    public List<WebElement> getElements(List<By> locatorSet) {
        return getElements(locatorSet, false);
    }
}
