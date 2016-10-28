package com.globant.testing.framework.web.selenium;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import com.globant.testing.framework.web.config.Framework;
import org.openqa.grid.internal.utils.configuration.StandaloneConfiguration;
import org.openqa.selenium.remote.server.SeleniumServer;
import org.slf4j.LoggerFactory;

import java.net.BindException;
import java.util.logging.LogManager;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Launches a single instance of Selenium Server programmatically
 *
 * @author Juan Krzemien
 */

public enum SeleniumServerStandAlone {

    INSTANCE;

    private final org.slf4j.Logger log = getLogger(SeleniumServerStandAlone.class);
    private SeleniumServer server;

    SeleniumServerStandAlone() {
        if (Framework.CONFIGURATION.WebDriver().isUseSeleniumGrid()) {
            log.info("Using Selenium Grid...");
            return;
        }
        log.info("Launching local Selenium Stand Alone Server...");
        try {
            StandaloneConfiguration options = new StandaloneConfiguration();
            options.debug = Framework.CONFIGURATION.isDebugMode();
            if (!options.debug) {
                ((Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME)).setLevel(Level.OFF);
                LogManager.getLogManager().getLogger("").setLevel(java.util.logging.Level.OFF);
            }
            this.server = new SeleniumServer(options);
            server.boot();
        } catch (Throwable t) {
            if (t.getCause() instanceof BindException) {
                log.warn("Already running. Will reuse...");
                return;
            }
            log.error("Failed to start the server", t);
        }
    }

    public void shutdown() {
        if (Framework.CONFIGURATION.WebDriver().isUseSeleniumGrid()) return;
        log.info("Shutting down local Selenium Stand Alone Server...");
        // Selenium Server already hooks itself to JVM Runtime to be removed upon exit
        //server.stop();
        //log.info("Done");
    }
}

