package com.globant.testing.framework.api.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.globant.testing.framework.api.config.interfaces.IConfig;
import com.globant.testing.framework.api.config.interfaces.IProxy;
import com.globant.testing.framework.api.logging.Loggable;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

import static java.lang.Boolean.parseBoolean;
import static java.lang.String.valueOf;
import static java.lang.System.getProperty;

/**
 * @author Juan Krzemien
 */
@JsonSerialize
class Config implements IConfig, Loggable {

    @JsonProperty
    private boolean isDebugMode = false;

    @JsonProperty
    private Proxy proxy = new Proxy();

    @JsonProperty
    private String baseUrl = "";

    Config() throws MalformedURLException {
    }

    @Override
    public boolean isDebugMode() {
        return parseBoolean(getProperty("DEBUG_MODE", valueOf(isDebugMode)));
    }

    @Override
    public IProxy Proxy() {
        return proxy;
    }

    @Override
    public Optional<URL> getBaseUrl() {
        try {
            return Optional.of(new URL(baseUrl));
        } catch (MalformedURLException e) {
            getLogger().error(e.getMessage(), e);
        }
        return Optional.empty();
    }

}
