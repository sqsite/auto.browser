package nz.sqsite.auto.ui.data;

public class DataCleaner {
    private DataCleaner() {
    }

    public static void cleanData() {
        GlobalData.clean();
    }

    public static void cleanTestData() {
        GlobalData.cleanTestData();
    }
}
