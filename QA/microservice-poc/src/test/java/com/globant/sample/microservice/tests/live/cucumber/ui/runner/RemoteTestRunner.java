package com.globant.sample.microservice.tests.live.cucumber.ui.runner;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@CucumberOptions(
        strict = true,
        features = {"src/test/resources/Features/ui"},
        glue = {
                // Framework definitions
                "classpath:com.globant.testing.framework.cucumber.definitions",
                // This module definitions
                "classpath:com.globant.sample.microservice.tests.live.cucumber.ui.runner",
                "classpath:com.globant.sample.microservice.tests.live.cucumber.ui.definitions"
        },
        format = {"pretty", "html:target/live-ui-cucumber", "json:target/live-ui-cucumber.json"},
        //Only run scenarios with these tags (put tilde before @ to exclude tag)
        tags = {
                "~@ignore"
        }
)
@RunWith(Cucumber.class)
public class RemoteTestRunner {
}
