package nz.sqsite.auto.ui.tabsandwindows;

public class FirstWindow implements BrowserWindows {
    @Override
    public int getIndex(int totalNumberOfTabs) {
        return 1;
    }
}
