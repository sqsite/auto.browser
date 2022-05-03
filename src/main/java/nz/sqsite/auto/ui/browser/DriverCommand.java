package nz.sqsite.auto.ui.browser;

import org.openqa.selenium.WebDriver;

public class DriverCommand {

    private final WebDriver webDriver;

    public DriverCommand(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public void closeDriver(){
        webDriver.close();
        webDriver.quit();
    }

    public void navigateTo(String url) {
        webDriver.navigate().to(url);
    }
}
