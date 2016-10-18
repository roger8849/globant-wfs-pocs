package com.globant.testing.framework.web.test.pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.Locatable;
import org.openqa.selenium.internal.WrapsElement;
import org.openqa.selenium.support.pagefactory.internal.LocatingElementHandler;
import org.slf4j.Logger;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;

import static java.lang.String.format;
import static java.lang.System.currentTimeMillis;
import static java.lang.reflect.Proxy.*;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Juan Krzemien
 */
class StalenessHandler extends WebDriverOperations implements InvocationHandler {

    private static final Logger LOG = getLogger(StalenessHandler.class);
    private static final String ELEMENT_IS_NOT_CLICKABLE_AT_POINT = "Element is not clickable at point";

    private final Object original;
    private final By locator;

    private StalenessHandler(Object original, By locator) {
        this.original = original;
        this.locator = locator;
    }

    static Object proxyFor(Object toProxy, ClassLoader loader, By locator) {
        if (!isProxyClass(toProxy.getClass())) return toProxy;
        StalenessHandler handler = new StalenessHandler(toProxy, locator);
        if (getInvocationHandler(toProxy) instanceof LocatingElementHandler) {
            return newProxyInstance(loader, new Class[]{WebElement.class, WrapsElement.class, Locatable.class}, handler);
        } else {
            return newProxyInstance(loader, new Class[]{List.class}, handler);
        }
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        LOG.debug(format("Executing method [%s] on [%s]...", method.getName(), locator));
        final long start = currentTimeMillis();
        try {
            return method.invoke(original, args);
        } catch (Exception e) {
            LOG.debug(format("Method [%s] throws: %s", method.getName(), getActualError(e)));
            if (isActionableException(e.getCause())) {
                try {
                    return method.invoke(waitFor(presenceOfElementLocated(locator)), args);
                } catch (Exception e1) {
                    LOG.error(format("Attempted recovery from exception but method [%s] kept failing: %s", method.getName(), getActualError(e1)));
                }
            }
            throw e.getCause();
        } finally {
            LOG.debug(format("Executed in [%s] ms", currentTimeMillis() - start));
        }
    }

    private String getActualError(Exception e) {
        if (e.getCause() == null) return e.getMessage();
        String msg = e.getCause().getMessage();
        return msg != null ? msg.split("\n")[0] : "<No information>";
    }

    private boolean isActionableException(Throwable t) {
        if (t == null) return false;
        boolean isStalled = t instanceof StaleElementReferenceException;
        boolean isWebDriver = t instanceof WebDriverException;
        boolean isNotClickable = t.getMessage().contains(ELEMENT_IS_NOT_CLICKABLE_AT_POINT);
        return isStalled || (isWebDriver && isNotClickable);
    }
}
