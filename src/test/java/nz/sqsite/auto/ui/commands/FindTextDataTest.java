package nz.sqsite.auto.ui.commands;

import nz.sqsite.auto.ui.browser.Browser;
import nz.sqsite.auto.ui.filehandlers.Finder;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.chrome.ChromeOptions;

import static nz.sqsite.auto.ui.webelement.ElementFinder.find;

public class FindTextDataTest {

    private Browser browser;

    @Before
    public void initialize() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setHeadless(true);
        browser = new Browser();
        browser.withOptions(chromeOptions).open("file://" + Finder.findFilePath("components/elements/texttypes.html"));
    }

    @After
    public void terminate() {
        browser.close();
    }



    @Test
    public void findTextBygetText() {
        String text = find("#pOne").getText();
        Assert.assertEquals("Text Value", text);
    }

    @Test
    public void findTextBygetValue() {
        String text = find("#pTwo").getText();
        Assert.assertEquals(find("#pTwo").getAttribute("value"), text);
    }

    @Test
    public void findTextByInnerHtml() {
        find("#pThree").clickByJS();
        String text = find("#pThree").getText();
        Assert.assertEquals(find("#pThree").getAttribute("innerHTML"), text);
    }

}
