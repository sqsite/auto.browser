package nz.sqsite.auto.ui.verification.expectations;

import org.openqa.selenium.By;

import java.util.List;

public class SoftAssertion {

    private SoftAssertion() {
    }

    public static Expectation softAssert(boolean isVisible, boolean isMultiple, List<By> locatorSet, Expectation expectation) {
        expectation.assertion(isVisible, isMultiple, locatorSet);
        return expectation;
    }
}
