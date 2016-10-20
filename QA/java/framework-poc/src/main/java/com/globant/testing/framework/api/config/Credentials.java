package com.globant.testing.framework.api.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Juan Krzemien
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Credentials {

    @JsonProperty
    private String username = "";

    @JsonProperty
    private String password = "";

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
