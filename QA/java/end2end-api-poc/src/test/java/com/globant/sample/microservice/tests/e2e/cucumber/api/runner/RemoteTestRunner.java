package com.globant.sample.microservice.tests.e2e.cucumber.api.runner;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@CucumberOptions(
        strict = true,
        features = {"src/test/resources/features"},
        glue = {
                // Framework definitions
                "classpath:com.globant.testing.framework.cucumber.definitions",
                // This module definitions
                "classpath:com.globant.sample.microservice.tests.cucumber.api.definitions",
                "classpath:com.globant.sample.microservice.tests.integration.live.cucumber.api.runner"
        },
        format = {"pretty", "html:target/live-api-cucumber", "json:target/live-api-cucumber.json"},
        //Only run scenarios with these tags (put tilde before @ to exclude tag)
        tags = {
                "~@ignore"
        }
)
@RunWith(Cucumber.class)
public class RemoteTestRunner {
}
