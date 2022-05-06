package nz.sqsite.auto.ui.commands;

import nz.sqsite.auto.ui.browser.Browser;
import nz.sqsite.auto.ui.filehandlers.Finder;
import org.junit.*;
import org.openqa.selenium.chrome.ChromeOptions;

import static nz.sqsite.auto.ui.webelement.ElementFinder.find;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ClickByJSTest {


    @Before
    public void initialize() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setHeadless(true);
        
        Browser.withOptions(chromeOptions).open("file://" + Finder.findFilePath("components/button/button.html"));
    }

    @After
    public void terminate() {
        Browser.close();
    }

    @Test
    public void testClickByJS() {
        find("#test_button_id").clickByJS();
        assertThat("Automation did not work, button cannot be clicked",  find("#test_button_id").getText(), equalTo("Button Clicked"));
    }

    @Test(expected = ComparisonFailure.class)
    public void testJSClickCannotDoubleClick() {
        find("#double_click_button").clickByJS();
        Assert.assertEquals("Button Double Clicked", find("#double_click_button").getText());
    }
}
