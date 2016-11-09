package com.globant.sample.cucumber.integration.cassandra.runner;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.globant.sample.cassandra.config.CassandraConfig;
import cucumber.api.CucumberOptions;
import cucumber.runtime.arquillian.CukeSpace;
import org.arquillian.cube.HostPort;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.rules.SpringClassRule;
import org.springframework.test.context.junit4.rules.SpringMethodRule;

import javax.annotation.PostConstruct;

import static java.lang.String.format;

@CucumberOptions(
        strict = true,
        features = {"src/it/resources/features"},
        glue = {"classpath:com.globant.sample.cucumber.integration.cassandra.definitions"},
        format = {"pretty", "html:target/integration-cucumber", "json:target/integration-cucumber.json"},
        //Only run scenarios with these tags (put tilde before @ to exclude tag)
        tags = {
                "~@ignore"
        }
)
@ContextConfiguration(classes = {RunTest.TestConfig.class})
@RunWith(CukeSpace.class)
@RunAsClient
public class RunTest {

    @ClassRule
    public static final SpringClassRule SPRING_CLASS_RULE = new SpringClassRule();

    @Rule
    public final SpringMethodRule springMethodRule = new SpringMethodRule();

    @HostPort(containerName = "cassandra", value = 9042)
    private static int cassandraPort;

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

        // You can also use @ComponentScan in this config class to autodetect this bean
        // I'm doing it here programmatically to make it more visible
        @Bean
        public SpringContextBridgedServices getServicesForTests(@Autowired ApplicationContext context) {
            SpringContextBridge springBride = new SpringContextBridge();
            springBride.setApplicationContext(context);
            return springBride;
        }
    }

}
