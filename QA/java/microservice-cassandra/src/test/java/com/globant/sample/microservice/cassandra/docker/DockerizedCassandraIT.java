package com.globant.sample.microservice.cassandra.docker;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.globant.sample.microservice.cassandra.config.CassandraConfig;
import com.globant.sample.microservice.cassandra.entity.LogReg;
import com.globant.sample.microservice.cassandra.repository.LogTextRepository;
import com.globant.testing.framework.api.logging.Loggable;
import com.palantir.docker.compose.DockerComposeRule;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.PostConstruct;

import static com.palantir.docker.compose.connection.waiting.SuccessOrFailure.success;
import static java.lang.String.format;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Sample integration test using EXCLUSIVELY Spring Boot testing framework.
 * <p>
 * "Integration test" as in Spring documentation. Sort of a multi class level testing. It is NOT UNIT TESTING.
 *
 * @author Juan Krzemien
 */
@SpringBootTest(classes = DockerizedCassandraIT.TestConfig.class)
public class DockerizedCassandraIT extends DockerIntegrationTest {

    @Autowired
    private LogTextRepository repository;

    @Test
    public void testSaveLog() {
        LogReg logReg = new LogReg();
        logReg.setId(13);
        logReg.setLogText("TEST!");
        repository.save(logReg);
        LogReg retrieved = repository.findOne(logReg.getId());
        assertNotNull("LogReg should not be null", retrieved);
        assertEquals("IDs should match", logReg.getId(), retrieved.getId());
        assertEquals("Text should match", logReg.getLogText(), retrieved.getLogText());
    }

    @Configuration
    static class TestConfig extends CassandraConfig {

        @Override
        protected int getPort() {
            return getDockerExternalPort();
        }

        @PostConstruct
        public void createKeySpace() throws Exception {
            try (Cluster cluster = Cluster.builder().addContactPoint("localhost").withPort(getDockerExternalPort()).build()) {
                try (Session session = cluster.newSession()) {
                    session.execute(format("CREATE KEYSPACE IF NOT EXISTS %s WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 1 };", "test"));
                }
            }
        }
    }

}