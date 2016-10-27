package com.globant.sample.microservice.tests.end2end.live.cucumber.ui.definitions;

import com.globant.sample.microservice.tests.end2end.live.cucumber.ui.pages.HomePage;
import com.globant.testing.framework.web.test.cucumber.StepsFor;
import cucumber.api.java.en.Given;

import static org.junit.Assert.assertTrue;


/**
 * @author Juan Krzemien
 */
public class HomePageSteps extends StepsFor<HomePage> {

    @Given("^User is at Google Home page$")
    public void userIsAtGoogleHomePage() throws Throwable {
        assertTrue("Home page is not present", getCurrentPageForStep().isVisible());
    }

}
