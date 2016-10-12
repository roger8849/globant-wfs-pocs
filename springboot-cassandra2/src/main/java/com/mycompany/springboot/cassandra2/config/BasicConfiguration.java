package com.mycompany.springboot.cassandra2.config;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.NettyOptions;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.querybuilder.Insert;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.mycompany.springboot.cassandra2.CassandraVersion;
import com.mycompany.springboot.cassandra2.entity.LogReg;
import com.mycompany.springboot.cassandra2.repository.LogTextRepository;
import io.netty.channel.EventLoopGroup;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.config.java.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;
import org.springframework.data.util.Version;

@SpringBootApplication
@EnableCassandraRepositories("com.mycompany.springboot.cassandra2.repository")
public class BasicConfiguration implements CommandLineRunner {

    public final static Version CASSANDRA_3_4 = Version.parse("3.4");

    @Autowired
    private CassandraOperations cassandraOperations;

    @Autowired
    private LogTextRepository logTextRepository;

    @Override
    public void run(String... strings) throws Exception {
        Logger log = Logger.getLogger(BasicConfiguration.class);
        log.info("Starting the app............");
        LogReg logReg = new LogReg();
        logReg.setId(5);
        logReg.setLogText("Test Crud Repo");
        logTextRepository.save(logReg);
        
        Insert insert = QueryBuilder.insertInto("logReg").value("id", 3L) //
                .value("logText", "Prueba")
                .ifNotExists(); //
        cassandraOperations.execute(insert);
        log.info("Finishing the app............");
    }

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

        @PostConstruct
        public void createKeySpace() throws Exception{

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
                    eventLoopGroup.shutdownGracefully(0, 0, TimeUnit.MILLISECONDS).syncUninterruptibly();
                }
            }).build();

            Session session = cluster.newSession();

            try {

                session.execute(String.format("CREATE KEYSPACE IF NOT EXISTS %s \n"
                        + "WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 1 };", "test"));
            } finally {
                session.close();
                cluster.close();
            }
        }
    }
}
