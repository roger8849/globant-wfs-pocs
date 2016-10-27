package com.globant.sample.microservice.tests.end2end.live.cucumber.ui.runner;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@CucumberOptions(
        strict = true,
        features = {"end2end-poc/src/test/resources/features"},
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
@RunWith(Cucumber.class)
public class TestRunner {
}