package com.globant.testing.framework.web.test.pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

/**
 * Public abstract class for modeling a *portion* (or *section*) of a Web Page using the Page Object pattern.
 *
 * @author Juan Krzemien
 */
public class PageElement extends PageCommon {

    private final By contextLocator;
    private WebElement contextElement;

    protected PageElement(By contextLocator) {
        this.contextLocator = contextLocator;
    }

    protected WebElement getContextElement() {
        if (contextElement == null) {
            this.contextElement = (WebElement) StalenessHandler.proxyFor(waitFor(visibilityOfElementLocated(contextLocator)), getClass().getClassLoader(), contextLocator);
        }
        return contextElement;
    }

    protected By getContextLocator() {
        return contextLocator;
    }

}
