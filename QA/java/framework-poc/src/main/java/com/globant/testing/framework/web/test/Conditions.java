package com.globant.testing.framework.web.test;

import org.openqa.selenium.support.ui.ExpectedCondition;

import java.util.function.Function;

public final class Conditions {

    private Conditions() {
    }

    public static <T> ExpectedCondition toBeTrue(T subject, Function<T, Boolean> condition) {
        return c -> condition.apply(subject);
    }
}
