package nz.sqsite.auto.ui.browser;

import nz.sqsite.auto.ui.data.Store;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.AbstractDriverOptions;

public class Driver {
    private WebDriver webDriver;

    public DriverCommand create(String browserType, AbstractDriverOptions<?> options){
        webDriver = new BrowserFactory().getBrowser(browserType, options);
        Store.storeTypeObject("Driver", webDriver);
        return new DriverCommand(webDriver);
    }

    public void close(){
        new DriverCommand(webDriver).closeDriver();
    }

    public static WebDriver getDriver(){
        return (WebDriver) Store.getTypeObject("Driver");
    }
}
