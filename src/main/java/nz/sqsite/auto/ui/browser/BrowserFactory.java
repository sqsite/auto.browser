package nz.sqsite.auto.ui.browser;

import nz.sqsite.auto.ui.utils.CheckString;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.opera.OperaOptions;
import org.openqa.selenium.remote.AbstractDriverOptions;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;

import java.util.Locale;

public class BrowserFactory {

    public WebDriver getBrowser(String browser, AbstractDriverOptions<?> options) {
        if(CheckString.isNullOrEmpty(browser)){
            return new Chrome().getDriver((ChromeOptions) options);
        }
        switch (browser.toUpperCase(Locale.ROOT)) {
            case "FIREFOX":
                return new Firefox().getDriver((FirefoxOptions) options);
            case "EDGE":
                return new Edge().getDriver((EdgeOptions) options);
            case "OPERA":
                return new Opera().getDriver((OperaOptions) options);
            case "SAFARI":
                return new SafariDriver((SafariOptions) options);
            default:
                return new Chrome().getDriver((ChromeOptions) options);
        }
    }

    public WebDriver getBrowser() {
        return getBrowser(null, null);
    }

}
