package com.globant.sample.microservice.tests.cucumber.api.definitions;

import com.globant.sample.microservice.Sample;
import com.globant.testing.framework.api.cucumber.AbstractApiDefinitions;
import com.globant.testing.framework.api.models.HateOasResource;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.response.Response;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Juan Krzemien
 */
public class SamplesApiDefinitions extends AbstractApiDefinitions {

    private HALSample samples;
    private Response response;

    @Given("^I query the GET endpoint \"([^\"]*)\"$")
    public void iQueryTheEndpointUsingAGETOperation(String endpoint) throws Throwable {
        response = spec().given().get(endpoint);
    }

    @When("^I retrieve the response$")
    public void iRetrieveTheResponse() throws Throwable {
        this.samples = response.getBody().as(HALSample.class);
    }

    @Then("^I should see a non empty list of samples$")
    public void iShouldSeeANonEmptyListOfSamples() throws Throwable {
        assertNotNull("Samples", samples);
        assertTrue("Expected number of samples did not match", samples.embedded.get("samples").size() > 0);
    }

    @Then("^I should see (\\d+) samples in the response$")
    public void iShouldSeeSamplesInTheResponse(int count) throws Throwable {
        assertNotNull("Samples", samples);
        assertEquals("Expected number of samples did not match", count, samples.embedded.get("samples").size());
    }

    @And("^Response should contain: (.+)$")
    public void responseShouldContainInLine(List<String> data) throws Throwable {
        responseContainsList(data);
    }

    @And("^Response should contain:$")
    public void responseShouldContain(List<String> data) throws Throwable {
        responseContainsList(data);
    }

    private void responseContainsList(List<String> data) {
        samples.embedded.get("samples").forEach(s -> {
            String value = s.getContent().getContent();
            assertTrue(String.format("Samples did not contain [%s] from provided data", value), data.contains(value));
        });
    }

    // Wrapper for HateOAS compatibility
    static class HALSample extends HateOasResource<Sample> {
    }
}
