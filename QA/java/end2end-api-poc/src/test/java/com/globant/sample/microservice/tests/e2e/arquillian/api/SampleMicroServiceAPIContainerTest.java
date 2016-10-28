package com.globant.sample.microservice.tests.e2e.arquillian.api;

import com.globant.sample.microservice.tests.models.Sample;
import io.restassured.RestAssured;
import org.arquillian.cube.HostIp;
import org.arquillian.cube.HostPort;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.not;

/**
 * @author Juan Krzemien
 */
@RunWith(Arquillian.class)
public class SampleMicroServiceAPIContainerTest {

    @HostPort(containerName = "microservice-poc", value = 8080)
    private int microServicePort;

    @HostPort(containerName = "cassandra", value = 9042)
    private int cassandraPort;

    @HostIp
    private String ip;

    @Test
    public void shouldRetrieveFirstSample() {
        final Sample sample = RestAssured.get("http://" + ip + ":" + microServicePort + "/samples/1").as(Sample.class);
        assertThat(sample.getContent(), is("RUSO SAMPLE CONTENT"));
    }

    @Test
    public void shouldRetrieveCassandraPortFromContainer() {
        assertThat(cassandraPort, is(not(0)));
    }

}