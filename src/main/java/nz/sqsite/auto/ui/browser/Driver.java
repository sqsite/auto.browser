package nz.sqsite.auto.ui.browser;

import nz.sqsite.auto.ui.data.Store;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.AbstractDriverOptions;

public class Driver {
    private final Logger logger = LogManager.getLogger(Driver.class);

    private WebDriver webDriver;

    public DriverCommand create(String browserType, AbstractDriverOptions<?> options){
        webDriver = new BrowserFactory().getBrowser(browserType, options);
        Store.storeTypeObject("Driver", webDriver);
        return new DriverCommand(webDriver);
    }

    public void close(){
        logger.info("Quitting Driver");
        new DriverCommand(webDriver).closeDriver();
    }

    public static WebDriver getDriver(){
        return (WebDriver) Store.getTypeObject("Driver");
    }
}
