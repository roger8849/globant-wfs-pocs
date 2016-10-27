package com.globant.sample.microservice.cassandra.docker;

import com.globant.testing.framework.api.logging.Loggable;
import com.palantir.docker.compose.DockerComposeRule;
import org.junit.ClassRule;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import static com.palantir.docker.compose.connection.waiting.SuccessOrFailure.success;

/**
 * Extend this class to create a new Docker based integration test suite (class)
 *
 * @author Juan Krzemien
 */
@RunWith(SpringRunner.class)
public abstract class DockerIntegrationTest implements Loggable {

    @ClassRule
    public static DockerComposeRule docker = DockerComposeRule.builder()
            .file("src/test/resources/docker-compose.yml")
            .waitingForService("cassandra", c -> {
                try {
                    Thread.sleep(16000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return success();
            })
            //.waitingForService("cassandra", c -> fromBoolean(c.port(9042).isListeningNow(), "Port 9042 closed"))
            .saveLogsTo("target/docker/dockerComposeRule")
            .build();

    protected static int getDockerExternalPort() {
        return docker.containers()
                .container("cassandra")
                .port(9042)
                .getExternalPort();
    }

}
