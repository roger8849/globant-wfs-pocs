package com.globant.cucumber.formatter.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class XRayTest {

    @JsonProperty("testKey")
    private String testKey = "<UNDEFINED>";

    @JsonProperty("start")
    private String start;

    @JsonProperty("finish")
    private String finish;

    @JsonProperty("comment")
    private String comment;

    @JsonProperty("status")
    private String status;

    @JsonProperty("evidences")
    private List<XRayEvidence> evidences = new ArrayList<>();

    @JsonProperty("examples")
    private List<String> examples = new ArrayList<>();

    @JsonProperty("steps")
    private List<XRayStep> steps = new ArrayList<>();

    /**
     * @return The testKey
     */
    @JsonProperty("testKey")
    public String getTestKey() {
        return testKey;
    }

    /**
     * @param testKey The testKey
     */
    @JsonProperty("testKey")
    public void setTestKey(String testKey) {
        this.testKey = testKey;
    }

    /**
     * @return The start
     */
    @JsonProperty("start")
    public String getStart() {
        return start;
    }

    /**
     * @param start The start
     */
    @JsonProperty("start")
    public void setStart(String start) {
        this.start = start;
    }

    /**
     * @return The finish
     */
    @JsonProperty("finish")
    public String getFinish() {
        return finish;
    }

    /**
     * @param finish The finish
     */
    @JsonProperty("finish")
    public void setFinish(String finish) {
        this.finish = finish;
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

    /**
     * @return The examples
     */
    @JsonProperty("examples")
    public List<String> getExamples() {
        return examples;
    }

    /**
     * @param examples The examples
     */
    @JsonProperty("examples")
    public void setExamples(List<String> examples) {
        this.examples = examples;
    }

    /**
     * @return The steps
     */
    @JsonProperty("steps")
    public List<XRayStep> getSteps() {
        return steps;
    }

    /**
     * @param steps The steps
     */
    @JsonProperty("steps")
    public void setSteps(List<XRayStep> steps) {
        this.steps = steps;
    }

}
