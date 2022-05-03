package nz.sqsite.auto.ui.browser;

import nz.sqsite.auto.ui.data.DataCleaner;
import nz.sqsite.auto.ui.data.WaitTime;
import nz.sqsite.auto.ui.supplier.ObjectSupplier;
import nz.sqsite.auto.ui.tabsandwindows.Tabs;
import nz.sqsite.auto.ui.tabsandwindows.Windows;
import nz.sqsite.auto.ui.wait.Activity;
import org.openqa.selenium.remote.AbstractDriverOptions;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static nz.sqsite.auto.ui.data.GlobalData.addData;
import static nz.sqsite.auto.ui.data.WaitTimeData.setWaitTime;

public class Browser {

    private String browserType;
    private AbstractDriverOptions<?> options;
    private Duration explicitWait;

    private final HashMap<Activity, Boolean> activityMap = new HashMap<>();
    private final Driver driver = new Driver();


    public void close() {
        try {
            driver.close();
        } finally {
            Tabs.remove();
            Windows.remove();
            ObjectSupplier.flushInstances();
            DataCleaner.cleanData();
        }
    }


    public Browser type(String browserType) {
        this.browserType = browserType;
        return this;
    }

    public Browser type(BrowserTypes browserType) {
        this.browserType = browserType.getBrowserName();
        return this;
    }

    public Browser withWaitTime(Duration explicitWait) {
        this.explicitWait = explicitWait;
        return this;
    }

    public Browser pageBackGroundActivity(Activity activity, boolean enable){
        activityMap.put(activity, enable);
        return this;
    }



    public Browser withOptions(AbstractDriverOptions<?> options) {
        this.options = options;
        return this;
    }

    public void open(String url) {
        if(explicitWait != null){
            setWaitTime(WaitTime.DEFAULT_WAIT_TIME, String.valueOf(explicitWait.toMillis()));
        }

        if(!activityMap.isEmpty()){
            activityMap.forEach((k, v) -> addData(k.getActivityType(), String.valueOf(v)));
        }
        driver.create(browserType, options).navigateTo(url);
    }
}


