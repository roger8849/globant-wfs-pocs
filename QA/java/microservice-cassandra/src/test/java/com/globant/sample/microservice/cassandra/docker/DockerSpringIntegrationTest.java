package com.globant.sample.microservice.cassandra.docker;

import com.globant.testing.framework.api.logging.Loggable;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.rules.SpringClassRule;
import org.springframework.test.context.junit4.rules.SpringMethodRule;

/**
 * Extend this class to create a new Docker based integration test suite (class)
 *
 * @author Juan Krzemien
 */
@RunWith(Arquillian.class)
public abstract class DockerSpringIntegrationTest implements Loggable {

    @ClassRule
    public static final SpringClassRule springClassRule = new SpringClassRule();

    @Rule
    public final SpringMethodRule springMethodRule = new SpringMethodRule();
}
