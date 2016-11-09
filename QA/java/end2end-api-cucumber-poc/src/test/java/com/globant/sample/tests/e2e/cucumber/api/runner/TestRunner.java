package com.globant.sample.tests.e2e.cucumber.api.runner;

import cucumber.api.CucumberOptions;
import cucumber.runtime.arquillian.CukeSpace;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.junit.runner.RunWith;

@CucumberOptions(
        strict = true,
        features = {"src/test/resources/features"},
        glue = {"classpath:com.globant.sample.tests.e2e.cucumber.api"},
        format = {"pretty", "html:target/live-api-cucumber", "json:target/live-api-cucumber.json"},
        //Only run scenarios with these tags (put tilde before @ to exclude tag)
        tags = {
                "~@ignore"
        }
)
@RunWith(CukeSpace.class)
@RunAsClient
public class TestRunner {
}
