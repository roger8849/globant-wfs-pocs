package com.globant.sample.cassandra.config;

import com.globant.sample.cassandra.entity.LogReg;
import com.globant.sample.cassandra.repository.LogTextRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.config.java.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

/**
 * @author Juan Krzemien
 */
@Configuration
@EnableCassandraRepositories(basePackageClasses = LogTextRepository.class)
public class CassandraConfig extends AbstractCassandraConfiguration {

    @Override
    public String getKeyspaceName() {
        return "test";
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