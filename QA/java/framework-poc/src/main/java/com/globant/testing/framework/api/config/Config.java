package com.globant.testing.framework.api.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.globant.testing.framework.api.config.interfaces.IConfig;
import com.globant.testing.framework.api.config.interfaces.IProxy;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static java.lang.Boolean.parseBoolean;
import static java.lang.String.valueOf;
import static java.lang.System.getProperty;

/**
 * @author Juan Krzemien
 */
@JsonSerialize
class Config implements IConfig {

    private static final String ACTIVE_ENVIRONMENT_ERROR = "Active environment and environments key names do not match";

    @JsonProperty
    private boolean isDebugMode;

    @JsonProperty
    private String activeEnvironment;

    @JsonProperty
    private Map<String, Environment> environments = new HashMap<>();

    @JsonProperty
    private IProxy proxy;

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
    public Environment getActiveEnvironment() {
        return Optional.ofNullable(environments.get(activeEnvironment)).orElseThrow(() -> new RuntimeException(ACTIVE_ENVIRONMENT_ERROR));
    }

    public void setActiveEnvironment(String name, Environment environment) {
        this.activeEnvironment = name;
        environments.put(activeEnvironment, environment);
    }

}
