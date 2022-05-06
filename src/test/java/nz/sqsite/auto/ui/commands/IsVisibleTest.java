package nz.sqsite.auto.ui.commands;

import nz.sqsite.auto.ui.browser.Browser;
import nz.sqsite.auto.ui.filehandlers.Finder;
import org.junit.*;
import org.openqa.selenium.chrome.ChromeOptions;

import static nz.sqsite.auto.ui.verification.expectations.Expectation.toBeInteractable;
import static nz.sqsite.auto.ui.webelement.ElementFinder.find;
import static nz.sqsite.auto.ui.webelement.ElementFinder.findAll;

public class IsVisibleTest {

    @Before
    public void initialize() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setHeadless(true);
        
        Browser.withOptions(chromeOptions).open("file://" + Finder.findFilePath("components/elements/elements.html"));
    }

    @After
    public void terminate() {
        Browser.close();
    }

    @Test @Ignore
    public void isPresentTest() {
        Assert.assertTrue(findAll("title:hidden").isPresent());
    }

    @Test
    public void isDisplayedTest() {
        find("title:hiddenX").invisibleElement().expecting(toBeInteractable);
        Assert.assertTrue(find("name:display_test").isDisplayed());
    }
}
