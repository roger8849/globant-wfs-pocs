package com.globant.sample.tests.e2e.arquillian.api;

import com.globant.sample.tests.e2e.arquillian.api.models.Sample;
import org.arquillian.cube.HostIp;
import org.arquillian.cube.HostPort;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Juan Krzemien
 */
public class SampleE2EApiTest extends End2EndApiTestBase {

    @HostPort(containerName = "cassandra", value = 9042)
    private int cassandraPort;

    @HostPort(containerName = "db", value = 5432)
    private int postgresPort;

    @HostIp
    private String ip;

    @Test
    public void shouldRetrieveFirstSample() {
        final Sample sample = client().get("/samples/1").as(Sample.class);
        assertThat(sample.getContent(), is("RUSO SAMPLE CONTENT"));
    }

    @Test
    public void shouldRetrieveCassandraPortFromContainer() {
        assertThat(cassandraPort, is(not(0)));
    }

    @Test
    public void shouldRetrievePostgresPortFromContainer() {
        assertThat(postgresPort, is(not(0)));
    }

}