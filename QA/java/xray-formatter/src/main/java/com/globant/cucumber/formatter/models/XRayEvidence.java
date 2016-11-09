package com.globant.cucumber.formatter.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class XRayEvidence {

    @JsonProperty("data")
    private String data;

    @JsonProperty("filename")
    private String filename;

    @JsonProperty("contentType")
    private String contentType;

    /**
     * @return The data
     */
    @JsonProperty("data")
    public String getData() {
        return data;
    }

    /**
     * @param data The data
     */
    @JsonProperty("data")
    public void setData(String data) {
        this.data = data;
    }

    /**
     * @return The filename
     */
    @JsonProperty("filename")
    public String getFilename() {
        return filename;
    }

    /**
     * @param filename The filename
     */
    @JsonProperty("filename")
    public void setFilename(String filename) {
        this.filename = filename;
    }

    /**
     * @return The contentType
     */
    @JsonProperty("contentType")
    public String getContentType() {
        return contentType;
    }

    /**
     * @param contentType The contentType
     */
    @JsonProperty("contentType")
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

}
