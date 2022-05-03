package nz.sqsite.auto.ui.testsuites;


import nz.sqsite.auto.ui.browsers.OpenTest;
import nz.sqsite.auto.ui.commands.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;


@RunWith(Suite.class)
@Suite.SuiteClasses({

        OpenTest.class,

        // Commands Test
        CheckTest.class,
        ClearAndTypeTest.class,
        ClearTest.class,
        ClickByJSTest.class,
        ClickTest.class,
        DoubleClickTest.class,
        FindAllTextDataTest.class,
        IsVisibleTest.class,
        SelectTests.class,
        SendKeysTest.class,
        SetInnerHtmlTest.class,
        SetTextTest.class,
        UnCheckTest.class,

})
public class FunctionTestSuite {
}
