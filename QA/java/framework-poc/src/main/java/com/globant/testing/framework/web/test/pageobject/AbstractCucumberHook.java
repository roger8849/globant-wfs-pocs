package com.globant.testing.framework.web.test.pageobject;

import com.globant.testing.framework.api.logging.Loggable;
import com.globant.testing.framework.web.test.cucumber.Context;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

import static org.openqa.selenium.OutputType.BYTES;

/**
 * Default Cucumber base test class.
 * <p>
 * Based on Framework's configuration:
 * <p>
 * - If specified in config file, starts and stops local Selenium Server instance on JVM start/shutdown
 * <p>
 * - Provides screenshot retrieval functionality for Cucumber scenario hooks, for example
 * <p>
 * - Methods for creating and destroying browser sessions.
 * <p>
 *
 * @author Juan Krzemien
 */
public abstract class AbstractCucumberHook implements Loggable {


    protected byte[] takeScreenshot() {
        byte[] bytes = new byte[0];
        WebDriver driver = Drivers.INSTANCE.get();
        if (driver instanceof TakesScreenshot) {
            try {
                bytes = ((TakesScreenshot) driver).getScreenshotAs(BYTES);
            } catch (WebDriverException e) {
                getLogger().error("Could not take snapshot!", e);
            }
        }
        return bytes;
    }

    protected void recreateDriver() {
        Drivers.INSTANCE.destroy();
        Drivers.INSTANCE.create(Context.BROWSER_FROM_CONTEXT());
    }

    protected void destroyDriver() {
        Drivers.INSTANCE.destroy();
    }

}
