package nz.sqsite.auto.ui.elementfinder;

import nz.sqsite.auto.ui.browser.Browser;
import nz.sqsite.auto.ui.exceptions.TimeoutException;
import nz.sqsite.auto.ui.filehandlers.Finder;
import nz.sqsite.auto.ui.verification.conditions.Condition;
import nz.sqsite.auto.ui.verification.conditions.collections.CollectionsCondition;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.ArrayList;
import java.util.List;

import static nz.sqsite.auto.ui.verification.criteria.Criteria.*;
import static nz.sqsite.auto.ui.webelement.ElementFinder.find;

public class ThenFindAndThenFindAllTests {

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
    public void findThenFindThenFindTest() {
        String text = find("#testid1").thenFind("id:testid2").thenFind("id:testid3").thenFind("tagName:p").getText();
        Assert.assertEquals("Tester", text);
    }

    @Test
    public void findThenFindThenFindTestWithShouldHave() {
        find("#testid1").thenFind("id:testid2").thenFind("id:testid3").thenFind("tagName:p").shouldHave(Condition.exactText("Tester"));
    }

    @Test
    public void findThenFindThenFindTestWithShouldBeVisible() {
        find("#testid1").thenFind("id:testid2").thenFind("id:testid3").shouldBe(visible);
    }

    @Test(expected = TimeoutException.class)
    public void findThenFindThenFindTestWithShouldBeNotVisible() {
        find("#testid1").thenFind("id:testid2").thenFind("id:testid3").shouldBe(notVisible);
    }


    @Test
    public void findThenFindThenFindAttribute() {
        String text = find("#testid1").thenFind("id:testid2").thenFind("id:testid3").getAttribute("style");
        Assert.assertEquals("color: blue;", text);
    }

    @Test
    public void findThenFindThenFindTagName() {
        String tagName = find("#testid1").thenFind("id:testid2").thenFind("id:testid3").getTagName();
        Assert.assertEquals("div", tagName);
    }

    @Test
    public void findThenFindThenFindAll() {
        String text = find("#testid1").thenFind("id:testid2").thenFindAll("tagName:div").get(1).getText();
        Assert.assertEquals("QA", text);
    }

    @Test
    public void findThenFindThenFindThenFindAll() {
        String text = find("#testid1").thenFind("id:testid2").thenFind("id:testid3").thenFindAll("tagName:p").get(1).getText();
        Assert.assertEquals("Of", text);
    }

    @Test(expected = AssertionError.class)
    public void findThenFindThenFindThenFindAllError() {
        String text = find("#testid1").thenFind("id:testid2").thenFind("id:testid3").thenFindAll("tagName:p").get(1).getText();
        Assert.assertEquals("Automation", text);
    }

    @Test
    public void findThenFindAllShouldHave() {
        find("#testid1").thenFind("id:testid2").thenFind("id:testid3").thenFindAll("tagName:p").get(1).shouldHave(Condition.exactText("Of"));
    }

    @Test
    public void findThenFindAllShouldHaveSize() {
        find("#testid1").thenFind("id:testid2").thenFind("id:testid3").thenFindAll("tagName:p").shouldHave(CollectionsCondition.size(3));
    }

    @Test
    public void findThenFindAllShouldHaveSizeGreater() {
        find("#testid1").thenFind("id:testid2").thenFind("id:testid3").thenFindAll("tagName:p").shouldHave(CollectionsCondition.sizeGreaterThan(2));
    }

    @Test
    public void findThenFindAllShouldHaveSizeLessThan() {
        find("#testid1").thenFind("id:testid2").thenFind("id:testid3").thenFindAll("tagName:p").shouldHave(CollectionsCondition.sizeLessThan(4));
    }

    @Test
    public void findThenFindAllShoulBeVisible() {
        find("#testid1").thenFind("id:testid2").thenFind("id:testid3").thenFindAll("tagName:p").get(1).shouldBe(visible);
    }

    @Test(expected = TimeoutException.class)
    public void findThenFindAllShoulBeNotVisibleThrows() {
        find("#testid1").thenFind("id:testid2").thenFind("id:testid3").thenFindAll("tagName:p").get(1).shouldBe(notVisible);
    }

    @Test
    public void findThenFindAllShoulBeEnabled() {
        find("#testid1").thenFind("id:testid2").thenFind("id:testid3").thenFindAll("tagName:p").get(1).shouldBe(enabled);
    }

    @Test(expected = TimeoutException.class)
    public void findThenFindAllShoulBeEnabledThrows() {
        find("#testid1").thenFind("id:testid2").thenFind("id:testid3").thenFindAll("tagName:p").get(1).shouldBe(notEnabled);
    }

    @Test
    public void findThenFindAllText() {
        List<String> values = new ArrayList<>();
        values.add("Tester");
        values.add("Of");
        values.add("Automation");

        List<String> text = find("#testid1").thenFind("id:testid2").thenFind("id:testid3").thenFindAll("tagName:p").getAllText();
        Assert.assertEquals(values, text);
    }

}
