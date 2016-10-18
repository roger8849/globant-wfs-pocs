package com.globant.testing.framework.web.test.pageobject;

import com.comcast.zucchini.AbstractZucchiniTest;
import com.comcast.zucchini.TestContext;
import com.comcast.zucchini.ZucchiniOutput;
import com.globant.testing.framework.web.config.Framework;
import com.globant.testing.framework.web.enums.Browser;
import com.globant.testing.framework.web.selenium.SeleniumServerStandAlone;
import com.globant.testing.framework.web.test.cucumber.Context;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import static org.openqa.selenium.OutputType.BYTES;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * Default Cucumber base test class.
 * <p>
 * Based on Framework's configuration:
 * <p>
 * - Extends Zucchini to provide default test context values from selected browsers
 * <p>
 * - If specified, starts and stops local Selenium Server instance on JVM start/shutdown
 * <p>
 * - Provides screenshot retrieval functionality for Cucumber scenario hooks, for example
 * <p>
 * - Methods for creating and destroying browser sessions.
 * <p>
 *
 * @author Juan Krzemien
 */
@ZucchiniOutput
public abstract class AbstractCucumberTestRunner extends AbstractZucchiniTest {

    protected static final Logger LOG = getLogger(AbstractCucumberTestRunner.class);

    static {
        Runtime.getRuntime().addShutdownHook(new Thread(SeleniumServerStandAlone.INSTANCE::shutdown));
    }

    @Override
    public List<TestContext> getTestContexts() {
        Set<Browser> browsers = Framework.CONFIGURATION.AvailableDrivers();
        List<TestContext> contexts = new ArrayList<>();
        for (Browser browser : browsers) {
            Map<String, Object> beans = new ConcurrentHashMap<>();
            beans.put(Context.BROWSER, browser);
            contexts.add(new TestContext(browser.name(), beans));
        }
        return contexts;
    }

    protected void recreateDriver() {
        Drivers.INSTANCE.destroy();
        Drivers.INSTANCE.create(Context.BROWSER_FROM_CONTEXT());
    }

    protected void destroyDriver() {
        Drivers.INSTANCE.destroy();
    }

    protected byte[] takeScreenshot() {
        byte[] bytes = new byte[0];
        WebDriver driver = Drivers.INSTANCE.get();
        if (driver instanceof TakesScreenshot) {
            try {
                bytes = ((TakesScreenshot) driver).getScreenshotAs(BYTES);
            } catch (WebDriverException e) {
                LOG.error("Could not take snapshot!", e);
            }
        }
        return bytes;
    }
}
