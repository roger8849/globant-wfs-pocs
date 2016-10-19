package com.globant.testing.framework.api.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.globant.testing.framework.api.config.interfaces.Authenticable;
import com.globant.testing.framework.api.config.interfaces.Locatable;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author Juan Krzemien
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DataSource implements Locatable, Authenticable {

    @JsonProperty
    private String url;

    @JsonProperty
    private Credentials credentials;

    @Override
    public URL getUrl() throws MalformedURLException {
        return new URL(url);
    }

    @Override
    public Credentials getCredentials() {
        return credentials;
    }
}
