package com.globant.sample.microservice.tests.integration.spring;

import com.globant.sample.microservice.Sample;
import com.globant.sample.microservice.SampleMicroService;
import com.globant.sample.microservice.SampleRepository;
import com.globant.testing.framework.api.config.DataSource;
import com.globant.testing.framework.api.logging.Loggable;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collection;
import java.util.Currency;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

/**
 * Sample component test using EXCLUSIVELY Spring Boot testing framework.
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