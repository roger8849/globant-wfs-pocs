package com.globant.testing.framework.web.test.cucumber;

import com.globant.testing.framework.web.enums.Browser;
import com.globant.testing.framework.web.test.pageobject.PageObject;
import org.slf4j.Logger;

import java.util.Optional;

import static java.lang.String.format;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * Helper class to promote and retrieve POMs and Browsers into and from Test Context easily
 *
 * @author Juan Krzemien
 */
public final class Context {

    private final static String BROWSER = "Browser";
    private final static String PAGE_OBJECT = "PageObject";

    private static final Logger LOG = getLogger(Context.class);

    Context() {
    }

    public static void BROWSER_TO_CONTEXT(Browser browser) {
        LOG.debug(format("Promoting Browser [%s] to context...", browser.name()));
        TestContext context = TestContext.getCurrent();
        if (context == null) {
            context = new TestContext(browser.name());
            TestContext.setCurrent(context);
        }
        context.set(BROWSER, browser);
    }

    public static Browser BROWSER_FROM_CONTEXT() {
        TestContext context = TestContext.getCurrent();
        if (context == null) {
            BROWSER_TO_CONTEXT(Browser.CHROME);
        }
        Browser browser = TestContext.getCurrent().get(BROWSER);
        LOG.debug(format("Retrieving Browser [%s] from context...", browser.name()));
        return browser;
    }

    public static <T extends PageObject> T PAGE_TO_CONTEXT(T page) {
        PageObject current = TestContext.getCurrent().get(PAGE_OBJECT);
        if (current == null || current.getClass() != page.getClass()) {
            LOG.debug(format("Promoting Page Object [%s] to context...", page.getClass().getSimpleName()));
            TestContext.getCurrent().set(PAGE_OBJECT, page);
        }
        return page;
    }

    public static <T extends PageObject> Optional<T> PAGE_FROM_CONTEXT() {
        T page = TestContext.getCurrent().get(PAGE_OBJECT);
        if (page != null) {
            LOG.info(format("Retrieving Page Object [%s] from context...", page.getClass().getSimpleName()));
        } else {
            LOG.error("Attempted to retrieve a page from Context but there is none!");
        }
        return Optional.ofNullable(page);
    }
}


