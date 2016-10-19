package com.globant.sample.microservice.tests.end2end.live.cucumber.ui.runner;

import com.globant.testing.framework.web.test.pageobject.AbstractCucumberTestRunner;
import cucumber.api.CucumberOptions;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;

import static java.lang.String.format;

@CucumberOptions(
        strict = true,
        features = {"src/test/resources/features"},
        glue = {
                // Framework definitions
                "classpath:com.globant.testing.framework.web.test.cucumber.definitions",
                // This module definitions
                "classpath:com.globant.sample.microservice.tests.end2end.live.cucumber.ui.definitions",
                "classpath:com.globant.sample.microservice.tests.end2end.live.cucumber.ui.runner"
        },
        format = {"pretty", "html:target/live-ui-cucumber", "json:target/live-ui-cucumber.json"},
        //Only run scenarios with these tags (put tilde before @ to exclude tag)
        tags = {
                "~@ignore"
        }
)
public class TestRunner extends AbstractCucumberTestRunner {

    @Before
    public void cucumberSetUp(Scenario scenario) {
        LOG.info(format("Starting scenario [%s] execution", scenario.getName()));
        recreateDriver();
    }

    @After
    public void cucumberTearDown(Scenario scenario) {
        LOG.info(format("Finished scenario [%s] execution", scenario.getName()));
        if (scenario.isFailed()) {
            scenario.embed(takeScreenshot(), "image/png");
        }
        destroyDriver();
    }
}