package com.globant.testing.framework.web.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.globant.testing.framework.web.config.interfaces.IDriver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Juan Krzemien
 */

@JsonSerialize
class Driver implements IDriver {

    @JsonProperty
    private String driverServerPath = "";

    @JsonProperty
    private Map<String, Object> capabilities = new HashMap<>();

    @JsonProperty
    private List<String> arguments = new ArrayList<>();

    @Override
    public String getDriverServerPath() {
        return driverServerPath;
    }

    @Override
    public Map<String, Object> getCapabilities() {
        return capabilities;
    }

    @Override
    public List<String> getArguments() {
        return arguments;
    }

}
