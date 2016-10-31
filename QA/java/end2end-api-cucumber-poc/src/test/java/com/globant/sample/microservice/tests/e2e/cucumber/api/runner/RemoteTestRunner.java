package com.globant.sample.microservice.tests.e2e.cucumber.api.runner;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import cucumber.runtime.arquillian.CukeSpace;
import cucumber.runtime.arquillian.api.Features;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.junit.runner.RunWith;

@CucumberOptions(
        strict = true,
        features = {"src/test/resources/features"},
        glue = {
                // Framework definitions
                "classpath:com.globant.testing.framework.api.cucumber",
                // This module definitions
                "classpath:com.globant.sample.microservice.tests.e2e.cucumber.api.definitions",
                "classpath:com.globant.sample.microservice.tests.e2e.cucumber.api.runner"
        },
        format = {"pretty", "html:target/live-api-cucumber", "json:target/live-api-cucumber.json"},
        //Only run scenarios with these tags (put tilde before @ to exclude tag)
        tags = {
                "~@ignore"
        }
)
@RunWith(CukeSpace.class)
//@Features({"src/test/resources/features"})
@RunAsClient
public class RemoteTestRunner {
}
