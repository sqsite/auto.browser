package nz.sqsite.auto.ui.browser;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import static io.github.bonigarcia.wdm.WebDriverManager.getInstance;

class Firefox {
    public WebDriver getDriver(FirefoxOptions options) {
        getInstance(FirefoxDriver.class).setup();
        if(options == null){
            options = new FirefoxOptions();
        }
        return new FirefoxDriver(options);
    }
}
