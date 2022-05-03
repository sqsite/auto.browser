package nz.sqsite.auto.ui.commands;

import nz.sqsite.auto.ui.browser.Browser;
import nz.sqsite.auto.ui.counter.TimeCounter;
import nz.sqsite.auto.ui.filehandlers.Finder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.chrome.ChromeOptions;

import static nz.sqsite.auto.ui.data.GlobalData.addData;
import static nz.sqsite.auto.ui.webelement.ElementFinder.find;

public class ClickTest {

    private Browser browser;

    @Before
    public void initialize() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setHeadless(true);
        browser = new Browser();
        browser.withOptions(chromeOptions).open("file://" + Finder.findFilePath("components/button/button.html"));
    }

    @After
    public void terminate() {
        browser.close();
    }

    /*
     * For this test two work add an exception to throw in click method
     *  Paste the below code in the click try block
     *
            if(getData("test").equals("throw")) {
                throw new ElementClickInterceptedException("test");
            }
     */
    //@Test(expected = AssertionError.class)
    public void clickTimeOutTest() {
        String locator = "id:test_button_id";
        addData("test", "do not throw");
        find(locator).click();
        addData("test", "throw");
        TimeCounter timeCounter = new TimeCounter();
        try {
            find(locator).waitFor(4).click();
        } catch (RuntimeException e) {
            if (timeCounter.timeElapsed().getSeconds() < 10) {
                throw new AssertionError("Click time out did not work propertly");
            }
        }
    }

    /*
   * For this test two work add an exception to throw in click method
   *  Paste the below code in the click try block
   *
          if(getData("test").equals("throw")) {
              throw new ElementClickInterceptedException("test");
          }
   */
    @Test
    public void clickTimeOutTestDoNotThrows() {
        String locator = "id:test_button_id";
        addData("test", "do not throw");
        find(locator).click();
        addData("test", "throw");
        TimeCounter timeCounter = new TimeCounter();
        try {
            find(locator).waitFor(7).click();
        } catch (RuntimeException e) {
            if (timeCounter.timeElapsed().getSeconds() < 7) {
                throw new AssertionError("Click time out did not work propertly");
            }
        }
    }
}
