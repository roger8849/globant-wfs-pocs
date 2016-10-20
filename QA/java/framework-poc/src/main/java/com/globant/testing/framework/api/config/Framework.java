package com.globant.testing.framework.api.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.globant.testing.framework.api.config.interfaces.IConfig;
import com.globant.testing.framework.api.config.interfaces.IProxy;
import com.globant.testing.framework.api.logging.Loggable;

import java.io.IOException;
import java.io.InputStream;

import static java.lang.String.format;
import static java.lang.Thread.currentThread;

/**
 * Global Framework's configuration entry point.
 * <p>
 * Reads a <i>config.yml</i> file expected to be found in classpath (usually <i>src/test/resources</i> for a Maven module)
 *
 * @author Juan Krzemien
 */
public enum Framework implements IConfig, Loggable {

    CONFIGURATION;

    private static final String CONFIG_FILE = "apiConfig.yml";

    private final IConfig config;

    Framework() {
        Thread.currentThread().setName("Framework-Thread");
        getLogger().info("Initializing Framework configuration...");
        this.config = readConfig();
    }

    private IConfig readConfig() {
        ObjectMapper om = new ObjectMapper(new YAMLFactory());
        IConfig configuration = null;
        InputStream configFile = currentThread().getContextClassLoader().getResourceAsStream(CONFIG_FILE);
        try {
            if (configFile != null) {
                configuration = om.readValue(configFile, Config.class);
            } else {
                return new Config();
            }
        } catch (IOException e) {
            getLogger().error(format("Error parsing framework config file [%s]. Re-check!", CONFIG_FILE), e);
        }
        return configuration;
    }

    @Override
    public boolean isDebugMode() {
        return config.isDebugMode();
    }


    @Override
    public IProxy Proxy() {
        return config.Proxy();
    }

    @Override
    public Environment getActiveEnvironment() {
        return config.getActiveEnvironment();
    }
}
