package com.globant.testing.framework.api.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.globant.testing.framework.api.config.interfaces.Authenticable;
import com.globant.testing.framework.api.config.interfaces.HasDataSources;
import com.globant.testing.framework.api.config.interfaces.Locatable;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Juan Krzemien
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Environment implements Locatable, Authenticable, HasDataSources {

    @JsonProperty
    private String url = "http://www.invalid.com/";

    @JsonProperty
    private Credentials credentials = new Credentials();

    @JsonProperty
    private List<DataSource> dataSources = new ArrayList<>();

    @Override
    public URL getUrl() throws MalformedURLException {
        return new URL(url);
    }

    @Override
    public Credentials getCredentials() {
        return credentials;
    }

    @Override
    public List<DataSource> getDataSources() {
        return dataSources;
    }
}
