package nz.sqsite.auto.ui.tabsandwindows;

public class LastTab implements BrowserWindows {
    @Override
    public int getIndex(int totalNumberOfTabs) {
        return totalNumberOfTabs;
    }
}
