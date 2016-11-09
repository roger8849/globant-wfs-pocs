package com.globant.cucumber.formatter.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class XRay {

    @JsonProperty("info")
    private XRayInfo info = new XRayInfo();

    @JsonProperty("tests")
    private List<XRayTest> tests = new ArrayList<>();

    /**
     * @return The info
     */
    @JsonProperty("info")
    public XRayInfo getInfo() {
        return info;
    }

    /**
     * @param info The info
     */
    @JsonProperty("info")
    public void setInfo(XRayInfo info) {
        this.info = info;
    }

    /**
     * @return The tests
     */
    @JsonProperty("tests")
    public List<XRayTest> getTests() {
        return tests;
    }

    /**
     * @param tests The tests
     */
    @JsonProperty("tests")
    public void setTests(List<XRayTest> tests) {
        this.tests = tests;
    }

}
