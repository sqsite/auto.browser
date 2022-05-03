package nz.sqsite.auto.ui.verification.expectations;

import nz.sqsite.auto.ui.verification.expectations.collections.Expectations;
import org.openqa.selenium.By;

import java.util.List;

public class CollectionsSoftAssertion {

    private CollectionsSoftAssertion() {
    }

    public static Expectations softAssert(boolean isMultiple, List<By> locatorSet, Expectations expectations) {
        expectations.assertion(isMultiple, locatorSet);
        return expectations;
    }
}
