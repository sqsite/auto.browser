package nz.sqsite.auto.ui.commands;

import nz.sqsite.auto.ui.browser.Browser;
import nz.sqsite.auto.ui.filehandlers.Finder;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.chrome.ChromeOptions;

import static nz.sqsite.auto.ui.webelement.ElementFinder.find;


public class UnCheckTest {

    private Browser browser;

    @Before
    public void initialize() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setHeadless(true);
        browser = new Browser();
        browser.withOptions(chromeOptions)
                .open("file://" + Finder.findFilePath("components/checkbox/checkbox.html"));
    }

    @After
    public void terminate() {
        browser.close();
    }

    @Test
    public void checkboxUncheckTest() {
        boolean result = find("id:test_checkbox_id").unCheck().isSelected();
        Assert.assertFalse("Radio button is not unchecked", result);
    }
}
