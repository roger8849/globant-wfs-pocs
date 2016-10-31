package com.globant.sample.microservice.tests.integration.live;

import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.rules.SpringClassRule;
import org.springframework.test.context.junit4.rules.SpringMethodRule;

/**
 * Extend this class to create a new Docker based Spring integration test suite (class)
 *
 * @author Juan Krzemien
 */
@RunWith(Arquillian.class)
@TestPropertySource("classpath:test.properties")
@RunAsClient
public abstract class DockerSpringIntegrationTest implements Loggable {

    @ClassRule
    public static final SpringClassRule SPRING_CLASS_RULE = new SpringClassRule();

    @Rule
    public final SpringMethodRule springMethodRule = new SpringMethodRule();

}
