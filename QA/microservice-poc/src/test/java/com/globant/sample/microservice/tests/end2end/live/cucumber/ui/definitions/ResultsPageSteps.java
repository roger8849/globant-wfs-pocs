package com.globant.sample.microservice.tests.end2end.live.cucumber.ui.definitions;

import com.globant.sample.microservice.tests.end2end.live.cucumber.ui.pages.ResultsPage;
import com.globant.testing.framework.web.test.cucumber.StepsFor;
import cucumber.api.java.en.When;

/**
 * @author Juan Krzemien
 */
public class ResultsPageSteps extends StepsFor<ResultsPage> {

    @When("^User clicks the (\\d+) link result$")
    public void userClicksTheLinkResult(int linkNumber) throws Throwable {
        getCurrentPageForStep().selectResultNumber(linkNumber);
    }
}
