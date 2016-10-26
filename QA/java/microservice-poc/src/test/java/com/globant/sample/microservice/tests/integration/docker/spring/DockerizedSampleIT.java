package com.globant.sample.microservice.tests.integration.docker.spring;

import com.globant.sample.microservice.Sample;
import com.globant.sample.microservice.SampleMicroService;
import com.globant.sample.microservice.SampleRepository;
import com.globant.testing.framework.api.logging.Loggable;
import com.palantir.docker.compose.DockerComposeRule;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collection;

import static org.junit.Assert.*;

/**
 * Sample integration test using EXCLUSIVELY Spring Boot testing framework.
 * <p>
 * "Integration test" as in Spring documentation. Sort of a multi class level testing. It is NOT UNIT TESTING.
 * Includes the possibility of mocking out beans (not mandatory).
 * <p>
 * When no beans are mocked, results in a live integration test.
 *
 * @author Juan Krzemien
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SampleMicroService.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DockerizedSampleIT extends AbstractTransactionalJUnit4SpringContextTests implements Loggable {

    @ClassRule
    public static DockerComposeRule docker = DockerComposeRule.builder()
            .file("src/test/resources/docker-compose.yml")
            .build();


    @Autowired
    private SampleRepository repository;

    @Test
    public void testRetrieveSamples() {
        Collection<Sample> samples = (Collection<Sample>) repository.findAll();
        assertNotNull("Samples should not be null", samples);
        assertEquals("There should be 5 samples in repository", 5, samples.size());
        samples.forEach(sample -> {
            assertNotNull("Sample should not be null", sample);
            assertNotNull("Sample content should not be null", sample.getContent());
            assertFalse("Content should not be empty", sample.getContent().isEmpty());
            getLogger().info("Entry content: " + sample.getContent());
        });
    }

}