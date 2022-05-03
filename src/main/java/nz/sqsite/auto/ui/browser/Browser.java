package nz.sqsite.auto.ui.browser;

import nz.sqsite.auto.ui.data.WaitTime;
import nz.sqsite.auto.ui.exceptions.SetUpException;
import org.openqa.selenium.remote.AbstractDriverOptions;

import java.time.Duration;

import static nz.sqsite.auto.ui.data.WaitTimeData.setWaitTime;

public class Browser {

    private String browserType;
    private AbstractDriverOptions<?> options;
    private Duration explicitWait;
    private Duration implicitWait;

    private final Driver driver = new Driver();


    public void close() {
        driver.close();
    }


    public Browser type(String browserType) {
        this.browserType = browserType;
        return this;
    }

    public Browser type(BrowserTypes browserType) {
        this.browserType = browserType.getBrowserName();
        return this;
    }

    public Browser explicitWaitTime(Duration explicitWait) {
        this.explicitWait = explicitWait;
        return this;
    }

    private Browser implicitWaitTime(Duration explicitWait) {
        this.explicitWait = explicitWait;
        return this;
    }

    public Browser withOptions(AbstractDriverOptions<?> options) {
        this.options = options;
        return this;
    }

    public void open(String url) {
        if (explicitWait != null && implicitWait != null) {
            throw new SetUpException("Both explicit and implicit waits should not be set at the same time");
        }

        if(explicitWait != null){
            setWaitTime(WaitTime.DEFAULT_WAIT_TIME, String.valueOf(explicitWait.toMillis()));
        }
        driver.create(browserType, options).navigateTo(url);
    }
}


