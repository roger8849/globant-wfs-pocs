package com.globant.sample.microservice.cassandra.docker;

import com.globant.sample.microservice.cassandra.CassandraMicroservice;
import com.globant.sample.microservice.cassandra.entity.LogReg;
import com.globant.sample.microservice.cassandra.repository.LogTextRepository;
import com.globant.testing.framework.api.logging.Loggable;
import com.palantir.docker.compose.DockerComposeRule;
import com.palantir.docker.compose.connection.Ports;
import com.palantir.docker.compose.connection.waiting.HealthCheck;
import com.palantir.docker.compose.connection.waiting.HealthChecks;
import com.palantir.docker.compose.connection.waiting.SuccessOrFailure;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collection;

import static com.palantir.docker.compose.connection.waiting.SuccessOrFailure.failure;
import static com.palantir.docker.compose.connection.waiting.SuccessOrFailure.success;
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
@SpringBootTest(classes = CassandraMicroservice.class)
public class DockerizedCassandraIT implements Loggable {

    @ClassRule
    public static DockerComposeRule docker = DockerComposeRule.builder()
            .file("src/test/resources/docker-compose.yml")
            .waitingForService("cassandra", HealthChecks.toHaveAllPortsOpen())
            .waitingForHostNetworkedPort(9042, target -> target.isListeningNow() ? success() : failure("Port closed"))
            .build();

    @Autowired
    private LogTextRepository repository;

    @Test
    public void testRetrieveSamples() {
        Collection<LogReg> logs = (Collection<LogReg>) repository.findAll();
        assertNotNull("Samples should not be null", logs);
        assertEquals("There should be 5 samples in repository", 5, logs.size());
        logs.forEach(log -> {
            assertNotNull("Sample should not be null", log);
            assertNotNull("Sample content should not be null", log.getLogText());
            assertFalse("Content should not be empty", log.getLogText().isEmpty());
            getLogger().info("Entry content: " + log.getLogText());
        });
    }

}