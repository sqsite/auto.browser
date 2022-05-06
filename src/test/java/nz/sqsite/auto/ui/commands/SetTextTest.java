package nz.sqsite.auto.ui.commands;

import nz.sqsite.auto.ui.browser.Browser;
import nz.sqsite.auto.ui.filehandlers.Finder;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Keys;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

import static nz.sqsite.auto.ui.webelement.ElementFinder.find;

public class SetTextTest {


    @Before
    public void initialize() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setHeadless(true);
        Browser.withOptions(chromeOptions)
                .withWaitTime(Duration.ofSeconds(6))
                .open("file://" + Finder.findFilePath("components/textinput/textinput.html"));
    }

    @After
    public void terminate() {
        Browser.close();
    }

    @Test
    public void inputUsingSetText() {
        String textInputLocator = "id:myText1";
        find(textInputLocator).click().clear().setText("Watercare");
        String buttonLocator = "id:text_submit_button";
        find(buttonLocator).click();

        String textAfter = find("id:result").getText();
        Assert.assertEquals("", "Watercare QA Team", textAfter);
    }

    @Test(expected = RuntimeException.class)
    public void inputUsingSetTextShouldFail() {
        find("id:myText1").waitFor(6).click().clear().setText("Watercare" + Keys.TAB);
    }
}
