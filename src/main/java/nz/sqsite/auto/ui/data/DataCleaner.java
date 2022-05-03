package nz.sqsite.auto.ui.data;

import nz.sqsite.auto.ui.supplier.ObjectSupplier;
import nz.sqsite.auto.ui.tabsandwindows.Tabs;
import nz.sqsite.auto.ui.tabsandwindows.Windows;
import nz.sqsite.auto.ui.wait.Wait;

public class DataCleaner {
    private DataCleaner() {
    }

    public static void cleanData() {
        GlobalData.clean();
        Wait.removeWait();
        Tabs.remove();
        Windows.remove();
        ObjectSupplier.flushInstances();
    }

    public static void cleanTestData() {
        GlobalData.cleanTestData();
    }
}
