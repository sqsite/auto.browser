package nz.sqsite.auto.ui.commands;

import nz.sqsite.auto.ui.browser.Browser;
import nz.sqsite.auto.ui.filehandlers.Finder;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.chrome.ChromeOptions;

import static nz.sqsite.auto.ui.webelement.ElementFinder.find;


public class ClearAndTypeTest {

    private Browser browser;

    @Before
    public void initialize() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setHeadless(true);
        browser = new Browser();
        browser.withOptions(chromeOptions).open("file://" + Finder.findFilePath("components/textinput/textinput.html"));
    }

    @After
    public void terminate() {
        browser.close();
    }

    @Test
    public void clearAndTypeTest() {
        String textInputLocator = "id:myText1";
        find(textInputLocator).setText("ProvaTest");
        Assert.assertEquals("ProvaTest", find(textInputLocator).getText());

        find(textInputLocator).clearAndType("Hello World");
        Assert.assertEquals("Hello World", find(textInputLocator).getText());
    }
}
