package nz.sqsite.auto.ui.commands;

import nz.sqsite.auto.ui.browser.Browser;
import nz.sqsite.auto.ui.filehandlers.Finder;
import org.junit.*;
import org.openqa.selenium.chrome.ChromeOptions;

import static nz.sqsite.auto.ui.webelement.ElementFinder.find;

public class DoubleClickTest {

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

    @Test
    public void testForErrorMessage() {
        find("#double_click_button").doubleClick();
        Assert.assertEquals("Button Double Clicked", find("#double_click_button").getText());
    }

    @Test
    public void testDoubleClick() {
        find("#double_click_button").doubleClick();
        Assert.assertEquals("Button Double Clicked", find("#double_click_button").getText());
    }

    @Test(expected = ComparisonFailure.class)
    public void testDoubleClickFail() {
        find("#double_click_button").click();
        Assert.assertEquals("Button Double Clicked", find("#double_click_button").getText());
    }
}
