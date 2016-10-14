package com.globant.sample.microservice.tests.live.cucumber.runner;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@CucumberOptions(
        strict = true,
        features = {"src/test/resources/Features"},
        glue = {
                // Framework definitions
                "classpath:com.globant.testing.framework.cucumber.definitions",
                // This module definitions
                "classpath:com.globant.sample.microservice.tests.live.cucumber.runner",
                "classpath:com.globant.sample.microservice.tests.live.cucumber.definitions"
        },
        format = {"pretty", "html:target/cucumber", "json:target/cucumber.json"},
        //Only run scenarios with these tags (put tilde before @ to exclude tag)
        tags = {
                "~@ignore"
        }
)
@RunWith(Cucumber.class)
public class RemoteTestRunner {
}
