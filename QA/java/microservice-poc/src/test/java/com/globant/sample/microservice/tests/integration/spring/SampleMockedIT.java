package com.globant.sample.microservice.tests.integration.spring;

import com.globant.sample.microservice.SampleMicroService;
import com.globant.sample.microservice.SampleRepository;
import com.globant.testing.framework.api.logging.Loggable;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Currency;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Sample compoenent test using EXCLUSIVELY Spring Boot testing framework.
 *
 * Sort of a multi class level testing. It is NOT UNIT TESTING.
 * Includes the possibility of mocking out beans via Spring's @MockBean.
 *
 * @author Juan Krzemien
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SampleMockedIT.Overrides.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SampleMockedIT extends AbstractTransactionalJUnit4SpringContextTests implements Loggable {

    @Autowired
    private Map<Currency, Currency> aPotentiallyOverriddenObject;

    @Autowired
    private SampleRepository repository;

    @Test
    public void testOverridden() {
        assertNotNull("Object should not be null if secondary context was started", aPotentiallyOverriddenObject);
        assertEquals("This is not the bean I overrode", aPotentiallyOverriddenObject.getClass(), HashMap.class);
        /*
        * ...Do something with classes under test assuming that some parts are now mocked...
        */
    }

    @Configuration
    @Import({SampleMicroService.class})
    static class Overrides {

        @Bean
        Map<Currency, Currency> doOverride() {
            return new HashMap<>();
        }
    }

}