package com.globant.sample.microservice.tests.integration.local.cucumber.api.runner;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * @author Juan Krzemien
 */
@ContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class Hooks {

    @Before
    public void setUp(Scenario scenario) {

    }

    @After
    public void tearDown(Scenario scenario) {

    }
}
