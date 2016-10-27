package com.globant.testing.framework.web.enums;

import io.github.bonigarcia.wdm.*;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import static com.globant.testing.framework.web.config.Framework.CONFIGURATION;
import static com.globant.testing.framework.utils.Environment.is64Bits;
import static io.github.bonigarcia.wdm.Architecture.x32;
import static io.github.bonigarcia.wdm.Architecture.x64;
import static io.github.bonigarcia.wdm.DriverVersion.LATEST;

/**
 * Enumeration that defines the browsers supported by this framework
 *
 * @author Juan Krzemien
 */
public enum Browser {

    MARIONETTE {
        @Override
        public Capabilities getCapabilities() {
            if (!CONFIGURATION.WebDriver().isUseSeleniumGrid()) {
                MarionetteDriverManager.getInstance().setup(architecture, LATEST.toString());
            }
            DesiredCapabilities capabilities = DesiredCapabilities.firefox();
            capabilities.setCapability("marionette", true);
            return capabilities;
        }
    },
    FIREFOX {
        @Override
        public Capabilities getCapabilities() {
            return DesiredCapabilities.firefox();
        }
    },
    CHROME {
        @Override
        public Capabilities getCapabilities() {
            if (!CONFIGURATION.WebDriver().isUseSeleniumGrid()) {
                ChromeDriverManager.getInstance().setup(architecture, LATEST.toString());
            }
            DesiredCapabilities capabilities = DesiredCapabilities.chrome();

            ChromeOptions options = new ChromeOptions();
            options.addArguments(CONFIGURATION.Driver(this).getArguments());
            capabilities.setCapability(ChromeOptions.CAPABILITY, options);

            return capabilities;
        }
    },
    IE {
        @Override
        public Capabilities getCapabilities() {
            if (!CONFIGURATION.WebDriver().isUseSeleniumGrid()) {
                // Override architecture for IE. 64 bits version is known to misbehave...
                InternetExplorerDriverManager.getInstance().setup(x32, LATEST.toString());
            }
            return DesiredCapabilities.internetExplorer();
        }
    },
    EDGE {
        @Override
        public Capabilities getCapabilities() {
            if (!CONFIGURATION.WebDriver().isUseSeleniumGrid()) {
                /**
                 * FIXME: EdgeDriverManager fails to download the correct Edge driver
                 * Microsoft changed the download URL/page so now this Manager crap with its hardcoded HtmlUnit
                 * mini-script looks in the wrong place...I don't want the Insiders old version...
                 */
                EdgeDriverManager.getInstance().setup();
            }
            return DesiredCapabilities.edge();
        }
    },
    APPIUM {
        @Override
        public Capabilities getCapabilities() {
            return null;
        }
    },
    SAUCE_LABS {
        @Override
        public Capabilities getCapabilities() {
            return null;
        }
    };

    private static Architecture architecture = is64Bits() ? x64 : x32;

    public abstract Capabilities getCapabilities();

}
