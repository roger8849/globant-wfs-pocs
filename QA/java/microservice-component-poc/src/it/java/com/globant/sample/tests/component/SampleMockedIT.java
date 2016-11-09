package com.globant.sample.tests.component;

import com.globant.sample.Sample;
import com.globant.sample.SampleRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collection;

import static org.junit.Assert.*;

/**
 * Sample COMPONENT test using EXCLUSIVELY Spring Boot testing framework.
 * <p>
 * Sort of a multi class level testing. It is NOT UNIT TESTING.
 * Includes the possibility of mocking out beans via Spring's @MockBean.
 *
 * @author Juan Krzemien
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY, connection = EmbeddedDatabaseConnection.H2)
public class SampleMockedIT implements Loggable {

    @Autowired
    private SampleRepository repository;

    @Test
    public void testRetrieveSamplesFromOverridenDataSource() {
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