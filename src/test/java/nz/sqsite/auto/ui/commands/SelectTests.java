package nz.sqsite.auto.ui.commands;

import nz.sqsite.auto.ui.browser.Browser;
import nz.sqsite.auto.ui.filehandlers.Finder;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.chrome.ChromeOptions;

import static nz.sqsite.auto.ui.webelement.ElementFinder.find;

public class SelectTests {
    private Browser browser;

    @Before
    public void initialize() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setHeadless(true);
        browser = new Browser();
        browser.withOptions(chromeOptions).open("file://" + Finder.findFilePath("components/select/select.html"));
    }

    @After
    public void terminate() {
        browser.close();
    }

    @Test
    public void selectByTextTest() {
        String selectedText = find("#test_select_id").select("Books");
        Assert.assertEquals("Books", selectedText);
    }

    @Test
    public void selectByTextTestMultiple() {
        String selectedText = find("#test_select_id").select("Books");
        Assert.assertEquals("Books", selectedText);

        selectedText = find("#test_select_id").select("CSS");
        Assert.assertEquals("CSS", selectedText);
    }

    @Test
    public void selectByIndexTest() {
        String selectedText = find("#test_select_id").select(2);
        Assert.assertEquals("CSS", selectedText);
    }

    @Test
    public void selectByValueTest() {
        String selectedText = find("#test_select_id").selectByValue("java");
        Assert.assertEquals("Java", selectedText);
    }

}
