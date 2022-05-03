package nz.sqsite.auto.ui.commands;

import nz.sqsite.auto.ui.browser.Browser;
import nz.sqsite.auto.ui.filehandlers.Finder;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.chrome.ChromeOptions;

import static nz.sqsite.auto.ui.verification.conditions.Condition.exactText;
import static nz.sqsite.auto.ui.webelement.ElementFinder.find;


public class ClearTest {

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
    public void textInputText() {
        String textInputLocator = "id:myText1";
        find(textInputLocator).setText("ProvaTest");
        Assert.assertEquals("ProvaTest", find(textInputLocator).getText());

        find(textInputLocator).clear();
        Assert.assertEquals("", find(textInputLocator).getText());
    }

    @Test
    public void testLocatorStringBuilderContains() {
        find("input.contains.data-id.dataid123").setText("ProvaTest");
        find("input.contains.data-id.dataid123").shouldHave(exactText("ProvaTest"));
    }

    @Test
    public void testLocatorStringBuilderWith() {
        find("input.with.id.myText1").setText("ProvaTest");
        find("input.with.id.myText1").shouldHave(exactText("ProvaTest"));
    }

}
