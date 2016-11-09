package com.globant.cucumber.formatter.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class XRayStep {

    @JsonProperty("status")
    private String status;

    @JsonProperty("comment")
    private String comment;

    @JsonProperty("evidences")
    private List<XRayEvidence> evidences = new ArrayList<>();

    /**
     * @return The status
     */
    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    /**
     * @param status The status
     */
    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return The comment
     */
    @JsonProperty("comment")
    public String getComment() {
        return comment;
    }

    /**
     * @param comment The comment
     */
    @JsonProperty("comment")
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * @return The evidences
     */
    @JsonProperty("evidences")
    public List<XRayEvidence> getEvidences() {
        return evidences;
    }

    /**
     * @param evidences The evidences
     */
    @JsonProperty("evidences")
    public void setEvidences(List<XRayEvidence> evidences) {
        this.evidences = evidences;
    }

}
