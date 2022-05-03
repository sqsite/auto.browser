package nz.sqsite.auto.ui.webelement;

import nz.sqsite.auto.ui.browser.Driver;
import nz.sqsite.auto.ui.counter.TimeCounter;
import nz.sqsite.auto.ui.data.WaitTime;
import nz.sqsite.auto.ui.data.WaitTimeData;
import nz.sqsite.auto.ui.loggers.LoggerUtil;
import nz.sqsite.auto.ui.supplier.ObjectSupplier;
import nz.sqsite.auto.ui.wait.Activity;
import nz.sqsite.auto.ui.wait.ActivityWaiter;
import nz.sqsite.auto.ui.wait.ThreadSleep;
import nz.sqsite.auto.ui.wait.Wait;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;

import static nz.sqsite.auto.ui.data.GlobalData.getData;


public class FindWebElement extends IFrameHandler {

    private static final LoggerUtil log = getLogger(FindWebElement.class);

    private final ActivityWaiter activityWaiter = (ActivityWaiter) ObjectSupplier.instanceOf(ActivityWaiter.class);
    private WebDriver driver;
    private WebDriverWait wait;
    private WebDriverWait backgroundActivityWait;
    private TimeCounter timeCounter;

    public WebElement webElement(By elemLocator) {
        return webElement(elemLocator, true);
    }

    public WebElement webElement(By elemLocator, boolean ensureVisibilityOfElement) {
        wait = Wait.getWait();
        driver = Driver.getDriver();
        backgroundActivityWait = Wait.getBackgroundMaxWait();

        if (getBGCheck()) {
            waitForPageLoad(driver, backgroundActivityWait);
        }

        log.elementsLog("Searching for element " + elemLocator);

        List<WebElement> elements = driver.findElements(elemLocator);

        if (ensureVisibilityOfElement) {
            if (elements.isEmpty() || !elements.get(0).isDisplayed()) {
                findFrameOfElement(elemLocator);
            }
        } else {
            if (elements.isEmpty()) {
                findFrameOfElement(elemLocator, false);
            }
        }

        log.elementsLog("Found Element " + elemLocator);
        return foundElement(driver, elemLocator);
    }

    public WebElement webElements(By elemLocator, int index, boolean visibility) {
        wait = Wait.getWait();
        driver = Driver.getDriver();
        backgroundActivityWait = Wait.getBackgroundMaxWait();

        if (getBGCheck()) {
            waitForPageLoad(driver, backgroundActivityWait);
        }

        log.elementsLog("Searching for " + (index + 1) + " elements with locator " + elemLocator);

        switchToIframeOfElement(elemLocator, driver, visibility);

        List<WebElement> elements = driver.findElements(elemLocator);

        try {
            wait.until(d -> (d.findElements(elemLocator).size() - 1 >= index));
        } catch (TimeoutException e) {
            throw new TimeoutException(String.format("Expected %d elements, but found only %d element[s] in %d seconds", index + 1, elements.size(), getWaitTime()));
        }

        elements = wait.until(d -> d.findElements(elemLocator));

        if (elements.size() - 1 >= index) {
            return elements.get(index);
        } else {
            throw new TimeoutException(String.format("Expected %d elements, but found only %d element[s]", index + 1, elements.size()));
        }
    }

    public List<WebElement> webElements(By elemLocator, boolean visibility) {
        wait = Wait.getWait();
        driver = Driver.getDriver();
        backgroundActivityWait = Wait.getBackgroundMaxWait();

        log.elementsLog("Searching for elements " + elemLocator);

        if (getBGCheck()) {
            waitForPageLoad(driver, backgroundActivityWait);
        }

        log.elementsLog("Searching for elements " + elemLocator);

        switchToIframeOfElement(elemLocator, driver, visibility);

        List<WebElement> elements = wait.until(d -> d.findElements(elemLocator));

        if (!elements.isEmpty()) {
            log.elementsLog("Elements found " + elemLocator + " is [" + elements.size() + "]");
        } else {
            log.elementsLog("No elements found " + elemLocator);
        }
        return elements;
    }

    private void findFrameOfElement(By elementLocator) {
        findFrameOfElement(elementLocator, true);
    }

    private void findFrameOfElement(By elemLocator, boolean elementNeedToBeVisible) {

        Function<WebDriver, Boolean> findElement = d -> {
            if (getBGCheck()) {
                waitForPageLoad(d, backgroundActivityWait);
            }
            return switchToIframeOfElement(elemLocator, d, elementNeedToBeVisible);
        };

        try {
            wait.until(findElement);
        } catch (TimeoutException e) {
            throw new TimeoutException(String.format("Element not found using %s in %d seconds", elemLocator, getWaitTime()));
        } catch (RuntimeException e) {
            if (e.getMessage().contains("Cannot find context with specified id")) {
                log.elementsLog("Chromedriver iframe switching issue");
            }
            wait.until(findElement);
        }
    }


    private void waitForPageLoad(WebDriver driver, WebDriverWait wait) {
        try {
            activityWaiter.waitUntilDocReady(driver, wait);

            if (getJQueryCheck()) {
                activityWaiter.waitUntilJQueryReady(driver, wait);
            }

            if (getAngularCheck()) {
                activityWaiter.waitUntilAngularReady(driver, wait);
            }

            if (getAngular5Check()) {
                activityWaiter.waitUntilAngular5Ready(driver, wait);
            }

        } catch (Exception e) {
            log.elementsLog("Activity Waiter Exception Occurred");
        }
    }

    private WebElement foundElement(WebDriver driver, By byLocator) {
        if (timeCounter == null) {
            timeCounter = new TimeCounter();
        }

        List<WebElement> elements = driver.findElements(byLocator);

        try {
            //If we are looking for a visible element, it will be returned
            for (WebElement element : elements) {
                if (element.isDisplayed()) {
                    String jsHighLighter = "arguments[0].style.border='1px dotted green'";
                    ((JavascriptExecutor) driver).executeScript(jsHighLighter, element);
                    return element;
                }
            }

            //If no visible elements are found, then the first element is returned.
            if (!elements.isEmpty()) {
                String jsHighLighter = "arguments[0].style.border='0px dotted green'";
                ((JavascriptExecutor) driver).executeScript(jsHighLighter, elements.get(0));
                return elements.get(0);
            }

        } catch (RuntimeException e) {
            if (timeCounter.timeElapsed(Duration.ofSeconds(getWaitTime()))) {
                throw new TimeoutException(String.format("Element is not found using %s in %d seconds", byLocator, getWaitTime()));
            }
            ThreadSleep.forMilliS(1000);
            foundElement(driver, byLocator);
        } finally {
            timeCounter = null;
        }

        throw new TimeoutException(String.format("Element is not found using %s in %d seconds", byLocator, getWaitTime()));
    }

    private int getWaitTime() {
        String time = WaitTimeData.getWaitTime(WaitTime.EXPLICIT_WAIT_TIME) == null ? WaitTimeData.getWaitTime(WaitTime.DEFAULT_WAIT_TIME) : WaitTimeData.getWaitTime(WaitTime.EXPLICIT_WAIT_TIME);
        return Integer.parseInt(time);
    }

    protected boolean getBGCheck() {
        if (getData(Activity.ALL_ACTIVITIES_CHECK.getActivityType()) == null) {
            return true;
        }
        return Boolean.parseBoolean(getData(Activity.ALL_ACTIVITIES_CHECK.getActivityType()));
    }

    protected boolean getJQueryCheck() {
        if (getData(Activity.JQUERY_LOAD_WAITER.getActivityType()) == null) {
            return false;
        }
        return Boolean.parseBoolean(getData(Activity.JQUERY_LOAD_WAITER.getActivityType()));
    }

    protected boolean getAngularCheck() {
        if (getData(Activity.ANGULAR_CHECK.getActivityType()) == null) {
            return false;
        }
        return Boolean.parseBoolean(getData(Activity.ANGULAR_CHECK.getActivityType()));
    }

    protected boolean getAngular5Check() {
        if (getData(Activity.ANGULAR_5_CHECK.getActivityType()) == null) {
            return false;
        }
        return Boolean.parseBoolean(getData(Activity.ANGULAR_CHECK.getActivityType()));
    }
}
