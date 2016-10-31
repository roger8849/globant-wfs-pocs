package com.globant.sample.microservice.tests.e2e.arquillian.api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author Juan Krzemien
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Sample {

    private long id;

    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
