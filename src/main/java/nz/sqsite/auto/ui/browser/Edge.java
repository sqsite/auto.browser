package nz.sqsite.auto.ui.browser;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

import static io.github.bonigarcia.wdm.WebDriverManager.getInstance;

class Edge{

    public WebDriver getDriver(EdgeOptions options) {
        getInstance(EdgeDriver.class).arch64().setup();
        if(options == null){
            options = new EdgeOptions();
        }
        return new EdgeDriver(options);
    }
}
