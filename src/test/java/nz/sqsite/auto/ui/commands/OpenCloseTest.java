package nz.sqsite.auto.ui.commands;

import nz.sqsite.auto.ui.browser.Browser;
import nz.sqsite.auto.ui.browser.BrowserTypes;
import nz.sqsite.auto.ui.browser.Driver;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.opera.OperaOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class OpenCloseTest {

    private Browser browser;

    @Before
    public void init(){
        browser = new Browser();
    }

    @Test
    public void chromeOpenTest(){
        ChromeOptions options = new ChromeOptions();
        options.setHeadless(true);
        browser.withOptions(options).open("https://google.co.nz");
        String browserName = ((RemoteWebDriver) Driver.getDriver()).getCapabilities().getBrowserName();
        assertThat(browserName, is(equalTo("chrome")));
        browser.close();
    }

    @Test
    public void firefoxOpenTest(){
        FirefoxOptions options = new FirefoxOptions();
        options.setHeadless(true);
        browser.type(BrowserTypes.FIREFOX).withOptions(options).open("https://google.co.nz");
        String browserName = ((RemoteWebDriver) Driver.getDriver()).getCapabilities().getBrowserName();
        assertThat(browserName, is(equalTo("firefox")));
        browser.close();
    }

    @Test
    public void edgeOpenTest(){
        EdgeOptions options = new EdgeOptions();
        options.setHeadless(true);
        browser.type("edge").withOptions(options).open("https://google.co.nz");
        String browserName = ((RemoteWebDriver) Driver.getDriver()).getCapabilities().getBrowserName();
        assertThat(browserName, is(equalTo("msedge")));
        browser.close();
    }

    @Test
    public void operaOpenTest(){
        OperaOptions options = new OperaOptions();
        browser.type("opera").withOptions(options).open("https://google.co.nz");
        String browserName = ((RemoteWebDriver) Driver.getDriver()).getCapabilities().getBrowserName();
        assertThat(browserName, is(equalTo("opera")));
        browser.close();
    }
}
