package com.globant.sample.microservice.tests.integration.spring;

import com.globant.sample.microservice.Sample;
import com.globant.sample.microservice.SampleMicroService;
import com.globant.sample.microservice.SampleRepository;
import com.globant.testing.framework.logging.Loggable;
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
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Sample integration test using EXCLUSIVELY Spring Boot testing framework
 * <p>
 * "Integration test" as in Spring documentation. Sort of a multi class level testing. It is NOT UNIT TESTING.
 * Includes the possibility of mocking out beans. Not mandatory, will result in live tests.
 *
 * @author Juan Krzemien
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SampleIntegrationIntegrationTest.Overrides.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SampleIntegrationIntegrationTest extends AbstractTransactionalJUnit4SpringContextTests implements Loggable {

    @Autowired
    private Map<Currency, Currency> aPotentiallyOverriddenObject;

    @Autowired
    private SampleRepository repository;

    @Test
    public void testRetrieveSample() {
        List<Sample> samples = (List<Sample>) repository.findAll();
        assertNotNull("Samples should not be null", samples);
        assertEquals("There should be 5 samples in repository", 5, samples.size());
        samples.forEach(sample -> {
            assertNotNull("Sample should not be null", sample);
            assertNotNull("Sample content should not be null", sample.getContent());
            assertFalse("Content should not be empty", sample.getContent().isEmpty());
            getLogger().info("Entry content: " + sample.getContent());
        });
    }

    @Test
    public void testOverridden() {
        assertNotNull("Object should not be null if secondary context was started", aPotentiallyOverriddenObject);
        assertEquals("This is not the bean I overrode", aPotentiallyOverriddenObject.getClass(), HashMap.class);
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