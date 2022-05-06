package nz.sqsite.auto.ui.commands;

import nz.sqsite.auto.ui.browser.Browser;
import nz.sqsite.auto.ui.filehandlers.Finder;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.chrome.ChromeOptions;

import static nz.sqsite.auto.ui.webelement.ElementFinder.find;


public class CheckTest {


    @Before
    public void initialize() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setHeadless(true);
        Browser.withOptions(chromeOptions).open("file://" + Finder.findFilePath("components/checkbox/checkbox.html"));
    }

    @After
    public void terminate() {
        Browser.close();
    }

    @Test
    public void checkboxClickTest() {
        boolean result = find("id:test_checkbox_id").check().isSelected();
        Assert.assertTrue("Radio Button is not checked", result);
    }
}
