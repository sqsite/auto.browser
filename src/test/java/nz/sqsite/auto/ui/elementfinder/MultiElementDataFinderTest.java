package nz.sqsite.auto.ui.elementfinder;

import nz.sqsite.auto.ui.browser.Browser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.concurrent.TimeoutException;

import static nz.sqsite.auto.ui.verification.conditions.collections.CollectionsCondition.size;
import static nz.sqsite.auto.ui.webelement.ElementFinder.find;
import static nz.sqsite.auto.ui.webelement.ElementFinder.findAll;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;


public class MultiElementDataFinderTest {

    @Before
    public void initialize() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setHeadless(true);
        Browser.withOptions(chromeOptions).open("https://google.co.nz");
    }

    @After
    public void terminate() {
        Browser.close();
    }


    @Test
    public void testFindAllWithClick() {
        find("name:q").click().sendKeys("Selenium");
    }

    @Test
    public void testFindAllWithSetText() {
        findAll("name:q").get(0).click().setText("Selenium");
    }

    @Test
    public void testFindAllShouldNotThrowError() {
        int size = findAll("name:xxxx").size();
        System.out.println(size);
    }

    @Test
    public void testFindAllShouldHave() {
//        ChromeOptions chromeOptions = new ChromeOptions();
//        chromeOptions.setHeadless(true);
//        Browser 
//        Browser.withOptions(chromeOptions).open("https://google.co.nz");
        findAll("name:q").shouldHave(size(1));
//        Browser.close();
    }

//    public static void main(String[] args) {
//        new MultiElementDataFinderTest().testFindAllShouldHave();
//    }

    @Test(expected = TimeoutException.class)
    public void testFindAllShouldHaveThrowError() {
        findAll("name:q").shouldHave(size(5));
    }

    @Test
    public void testFindAllSize() {
        int size = findAll("name:q").size();
        assertThat(1, equalTo(size));
    }
}
