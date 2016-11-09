package com.globant.sample.cucumber.integration.cassandra.definitions;

import com.globant.sample.cassandra.entity.LogReg;
import com.globant.sample.cassandra.repository.LogTextRepository;
import com.globant.sample.cucumber.integration.cassandra.runner.SpringContextBridge;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Juan Krzemien
 */
public class Steps {

    private LogTextRepository repository = SpringContextBridge.services().getService(LogTextRepository.class);
    private LogReg retrieved;

    @Given("^I store a log with ID (\\d+) and text \"([^\"]*)\"$")
    public void step1(int id, String text) throws Throwable {
        LogReg logReg = new LogReg();
        logReg.setId(id);
        logReg.setLogText(text);
        repository.save(logReg);
    }

    @When("^I retrieve the log with ID (\\d+)$")
    public void step2(int id) throws Throwable {
        retrieved = repository.findOne(id);
        assertNotNull("LogReg should not be null", retrieved);
    }

    @Then("^I should see \"([^\"]*)\" in the retrieved log text$")
    public void step3(String expected) throws Throwable {
        assertEquals("Text should match", expected, retrieved.getLogText());
    }
}
