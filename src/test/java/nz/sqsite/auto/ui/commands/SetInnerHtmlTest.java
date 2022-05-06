package nz.sqsite.auto.ui.commands;

import nz.sqsite.auto.ui.browser.Browser;
import nz.sqsite.auto.ui.filehandlers.Finder;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

import static nz.sqsite.auto.ui.webelement.ElementFinder.find;


public class SetInnerHtmlTest {

    @Before
    public void initialize() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setHeadless(true);
        
        Browser.withOptions(chromeOptions)
                .withWaitTime(Duration.ofSeconds(7))
                .open("file://" + Finder.findFilePath("components/textinput/textinput.html"));
    }

    @After
    public void terminate() {
        Browser.close();
    }

    @Test
    public void textInputText() {
        String textInputLocator ="id:innerHtmlP";
        find(textInputLocator).setInnerHtml("Watercare");
        Assert.assertEquals(find(textInputLocator).getText(), "Watercare");
    }

}
