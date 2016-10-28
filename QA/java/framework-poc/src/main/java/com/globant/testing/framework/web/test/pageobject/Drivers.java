package com.globant.testing.framework.web.test.pageobject;

import com.globant.testing.framework.web.enums.Browser;
import com.globant.testing.framework.web.listeners.BasicWebDriverListener;
import com.globant.testing.framework.web.selenium.SeleniumServerStandAlone;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import java.util.Map;

import static com.globant.testing.framework.web.config.Framework.CONFIGURATION;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.openqa.selenium.remote.CapabilityType.PROXY;

/**
 * Package local (per thread) web driver instance initializer and container
 *
 * @author Juan Krzemien
 */
enum Drivers {

    INSTANCE;

    private static final ThreadLocal<WebDriver> driverPerThread = new ThreadLocal<>();
    private static final ThreadLocal<String> mainWindowHandlerPerThread = new ThreadLocal<>();

    private final WebDriverStrategyFactory factory = new WebDriverStrategyFactory();

    Drivers() {
        Runtime.getRuntime().addShutdownHook(new Thread(SeleniumServerStandAlone.INSTANCE::shutdown));
    }

    public WebDriver create(Browser desiredBrowser) {
        WebDriver instance = get();
        if (instance == null) {
            instance = factory.getDriverFor(desiredBrowser);
            driverPerThread.set(instance);
            mainWindowHandlerPerThread.set(instance.getWindowHandle());
        }
        return instance;
    }

    public WebDriver get() {
        return driverPerThread.get();
    }

    public void destroy() {
        WebDriver instance = driverPerThread.get();
        if (instance != null) {
            instance.quit();
        }
        driverPerThread.remove();
        mainWindowHandlerPerThread.remove();
    }

    public String getMainWindowHandler() {
        return mainWindowHandlerPerThread.get();
    }

    class WebDriverStrategyFactory {

        private WebDriver getDriverFor(Browser browser) {

            DesiredCapabilities capabilities = new DesiredCapabilities(browser.getCapabilities());

            Map<String, Object> driverConfig = CONFIGURATION.Driver(browser).getCapabilities();

            capabilities.merge(new DesiredCapabilities(driverConfig));

            // Set proxy settings, if any
            if (CONFIGURATION.Proxy().isEnabled()) {

                String proxyCfg = CONFIGURATION.Proxy().getHost() + ":" + CONFIGURATION.Proxy().getPort();

                Proxy proxy = new Proxy();
                proxy.setHttpProxy(proxyCfg)
                        .setFtpProxy(proxyCfg)
                        .setSslProxy(proxyCfg);

                capabilities.setCapability(PROXY, proxy);
            }

            return getDriverFor(capabilities);
        }

        private WebDriver getDriverFor(DesiredCapabilities capabilities) {
            // Create driver
            WebDriver driver = new RemoteWebDriver(CONFIGURATION.WebDriver().getRemoteURL(), capabilities);

            driver.manage().timeouts().implicitlyWait(CONFIGURATION.WebDriver().getImplicitTimeOut(), SECONDS);
            driver.manage().timeouts().pageLoadTimeout(CONFIGURATION.WebDriver().getPageLoadTimeout(), SECONDS);
            driver.manage().timeouts().setScriptTimeout(CONFIGURATION.WebDriver().getScriptTimeout(), SECONDS);

            driver.manage().window().maximize();

            if (CONFIGURATION.WebDriver().isUseListener()) {
                // Wrap the driver as an event firing one, and add basic listener
                final EventFiringWebDriver eventDriver = new EventFiringWebDriver(driver);
                eventDriver.register(new BasicWebDriverListener());
                driver = eventDriver;
            }

            return driver;
        }

    }

}
