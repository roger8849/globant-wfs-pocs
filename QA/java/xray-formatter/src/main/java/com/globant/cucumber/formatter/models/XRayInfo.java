package com.globant.cucumber.formatter.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class XRayInfo {

    @JsonProperty("summary")
    private String summary;

    @JsonProperty("description")
    private String description;

    @JsonProperty("version")
    private String version;

    @JsonProperty("user")
    private String user;

    @JsonProperty("revision")
    private String revision;

    @JsonProperty("startDate")
    private String startDate;

    @JsonProperty("finishDate")
    private String finishDate;

    /**
     * @return The summary
     */
    @JsonProperty("summary")
    public String getSummary() {
        return summary;
    }

    /**
     * @param summary The summary
     */
    @JsonProperty("summary")
    public void setSummary(String summary) {
        this.summary = summary;
    }

    /**
     * @return The description
     */
    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    /**
     * @param description The description
     */
    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return The version
     */
    @JsonProperty("version")
    public String getVersion() {
        return version;
    }

    /**
     * @param version The version
     */
    @JsonProperty("version")
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * @return The user
     */
    @JsonProperty("user")
    public String getUser() {
        return user;
    }

    /**
     * @param user The user
     */
    @JsonProperty("user")
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * @return The revision
     */
    @JsonProperty("revision")
    public String getRevision() {
        return revision;
    }

    /**
     * @param revision The revision
     */
    @JsonProperty("revision")
    public void setRevision(String revision) {
        this.revision = revision;
    }

    /**
     * @return The startDate
     */
    @JsonProperty("startDate")
    public String getStartDate() {
        return startDate;
    }

    /**
     * @param startDate The startDate
     */
    @JsonProperty("startDate")
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    /**
     * @return The finishDate
     */
    @JsonProperty("finishDate")
    public String getFinishDate() {
        return finishDate;
    }

    /**
     * @param finishDate The finishDate
     */
    @JsonProperty("finishDate")
    public void setFinishDate(String finishDate) {
        this.finishDate = finishDate;
    }

}
