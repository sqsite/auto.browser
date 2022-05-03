package nz.sqsite.auto.ui.commands;

import nz.sqsite.auto.ui.browser.Browser;
import nz.sqsite.auto.ui.filehandlers.Finder;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.ArrayList;
import java.util.List;

import static nz.sqsite.auto.ui.webelement.ElementFinder.findAll;

public class FindAllTextDataTest {

    private Browser browser;

    @Before
    public void initialize() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setHeadless(true);
        browser = new Browser();
        browser.withOptions(chromeOptions).open("file://" + Finder.findFilePath("components/elements/elements.html"));
    }

    @After
    public void terminate() {
        browser.close();
    }

    @Test
    public void findAllTextDataTest() {
        List<String> allTextData = findAll("css:#testid3 p").getAllText();
        List<String> expectedValues = new ArrayList<>();
        expectedValues.add("Tester");
        expectedValues.add("Of");
        expectedValues.add("Automation");
        Assert.assertEquals(expectedValues, allTextData);
    }

    @Test
    public void findAllTextDataTestWithValue() {
        List<String> allTextData = findAll("css:#testid5 p").getAllText();
        List<String> expectedValues = new ArrayList<>();
        expectedValues.add("Automation");
        expectedValues.add("Tester");
        Assert.assertEquals(expectedValues, allTextData);
    }

    @Test(expected = AssertionError.class)
    public void findAllTextDataTestFail() {
        List<String> allTextData = findAll("css:#testid3 p").getAllText();
        List<String> expectedValues = new ArrayList<>();
        expectedValues.add("Tester");
        expectedValues.add("Automation");
        Assert.assertEquals(expectedValues, allTextData);
    }
}
