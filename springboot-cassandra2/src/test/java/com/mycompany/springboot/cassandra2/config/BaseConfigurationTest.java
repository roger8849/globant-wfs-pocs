/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.springboot.cassandra2.config;

import com.datastax.driver.core.Session;
import com.mycompany.springboot.cassandra2.entity.LogReg;
import com.mycompany.springboot.cassandra2.repository.LogTextRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.config.java.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;
import org.springframework.data.util.Version;

/**
 *
 * @author sebastian.pacheco
 */
@SpringBootApplication
@EnableCassandraRepositories("com.mycompany.springboot.cassandra2.repository")
public class BaseConfigurationTest {

    public final static Version CASSANDRA_3_4 = Version.parse("3.4");

    @Autowired
    private CassandraOperations cassandraOperations;

    @Autowired
    private LogTextRepository logTextRepository;

    @Configuration
    @EnableCassandraRepositories
    static class CassandraConfig extends AbstractCassandraConfiguration {

        @Override
        public String getKeyspaceName() {
            return "test";
        }

        @Bean
        public CassandraTemplate cassandraTemplate(Session session) {
            return new CassandraTemplate(session);
        }

        @Override
        public String[] getEntityBasePackages() {
            return new String[]{LogReg.class.getPackage().getName()};
        }

        @Override
        public SchemaAction getSchemaAction() {
            return SchemaAction.CREATE_IF_NOT_EXISTS;
        }
    }

}
