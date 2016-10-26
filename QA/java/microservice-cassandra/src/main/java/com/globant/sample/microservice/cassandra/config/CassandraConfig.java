package com.globant.sample.microservice.cassandra.config;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.NettyOptions;
import com.datastax.driver.core.Session;
import com.globant.sample.microservice.cassandra.entity.LogReg;
import com.globant.sample.microservice.cassandra.repository.LogTextRepository;
import io.netty.channel.EventLoopGroup;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.config.java.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

import javax.annotation.PostConstruct;
import java.net.InetSocketAddress;
import java.net.Socket;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

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

    /*@PostConstruct
    public void createKeySpace() throws Exception {

        try (Socket socket = new Socket()) {
            socket.setTcpNoDelay(true);
            socket.setSoLinger(true, 0);
            socket.connect(new InetSocketAddress("localhost", 9042), 100);

        } catch (Exception e) {
            throw new Exception("Seems as Cassandra is not running at localhost:9042.",
                    e);
        }

        Cluster cluster = Cluster.builder().addContactPoint("localhost").withPort(9042).withNettyOptions(new NettyOptions() {
            @Override
            public void onClusterClose(EventLoopGroup eventLoopGroup) {
                eventLoopGroup.shutdownGracefully(0, 0, MILLISECONDS).syncUninterruptibly();
            }
        }).build();

        Session session = cluster.newSession();

        try {

            session.execute(String.format("CREATE KEYSPACE IF NOT EXISTS %s " +
                    "WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 1 };", "test"));
        } finally {
            session.close();
            cluster.close();
        }
    }*/
}