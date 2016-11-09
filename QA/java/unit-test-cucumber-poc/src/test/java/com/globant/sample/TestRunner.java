package com.globant.sample;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 * @author Juan Krzemien
 */
@CucumberOptions(
        strict = true,
        features = {"src/test/resources/features"},
        glue = {"classpath:com.globant.sample"},
        format = {"pretty", "html:target/unit-tests-cucumber", "json:target/unit-tests-cucumber.json"},
        plugin = {"com.globant.cucumber.formatter.CucumberXRayFormatter"},
        //Only run scenarios with these tags (put tilde before @ to exclude tag)
        tags = {
                "~@ignore"
        }
)
@RunWith(Cucumber.class)
public class TestRunner {
}
