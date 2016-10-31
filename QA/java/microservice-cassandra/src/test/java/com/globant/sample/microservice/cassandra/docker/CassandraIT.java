package com.globant.sample.microservice.cassandra.docker;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.globant.sample.microservice.cassandra.config.CassandraConfig;
import com.globant.sample.microservice.cassandra.entity.LogReg;
import com.globant.sample.microservice.cassandra.repository.LogTextRepository;
import org.arquillian.cube.HostPort;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;

import javax.annotation.PostConstruct;

import static java.lang.String.format;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Sample integration test using:
 *
 *  + Arquillian + Cube for Docker management
 *  + JUnit rules to spin up Spring container and inject dependencies
 *
 * @author Juan Krzemien
 */
@ContextConfiguration(classes = CassandraIT.TestConfig.class)
public class CassandraIT extends DockerSpringIntegrationTest {

    @HostPort(containerName = "cassandra", value = 9042)
    private static int cassandraPort;

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
            return cassandraPort;
        }

        @PostConstruct
        public void createKeySpace() throws Exception {
            try (Cluster cluster = Cluster.builder().addContactPoint("localhost").withPort(cassandraPort).build()) {
                try (Session session = cluster.newSession()) {
                    session.execute(format("CREATE KEYSPACE IF NOT EXISTS %s WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 1 };", "test"));
                }
            }
        }
    }

}