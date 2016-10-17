/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.springboot.cassandra2.repository;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.NettyOptions;
import com.datastax.driver.core.Session;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.mycompany.springboot.cassandra2.config.BaseConfigurationTest;
import com.mycompany.springboot.cassandra2.entity.LogReg;
import io.netty.channel.EventLoopGroup;
import java.util.concurrent.TimeUnit;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author sebastian.pacheco
 */
@RunWith(SpringRunner.class)
@SpringBootTest( classes = BaseConfigurationTest.class)
public class LogTextRepositoryTest {
    
    private Logger log = Logger.getLogger(LogTextRepositoryTest.class);
    
    @Autowired
    private LogTextRepository logTextRepository;
    
    @Before
    public void setUp(){
        log.info("SETTING UP THE TEST KEYSPACE");
        Cluster cluster = Cluster.builder().addContactPoint("localhost").withPort(9042).withNettyOptions(new NettyOptions() {
                @Override
                public void onClusterClose(EventLoopGroup eventLoopGroup) {
                    eventLoopGroup.shutdownGracefully(0, 0, TimeUnit.MILLISECONDS).syncUninterruptibly();
                }
            }).build();

            Session session = cluster.newSession();

            try {

                session.execute(String.format("CREATE KEYSPACE IF NOT EXISTS %s \n"
                        + "WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 1 };", "test_junit"));
            }catch(Exception e){
                log.error("KEYSPACE CREATION FAILED");
            } finally {
                session.close();
                cluster.close();
            }
    }
    
    @Test
    public void createLogTextTest(){
        LogReg row = new LogReg();
        row.setId(1);
        row.setLogText("Creation test");
        logTextRepository.save(row);
        LogReg result = logTextRepository.findOne(1);
        Assert.assertEquals(row.getId(),result.getId());
        Assert.assertEquals(row.getLogText(),result.getLogText());
        
    }
    
    
    @After
    public void cleanUp(){
        log.info("DESTROYING THE TEST KEYSPACE");
        Cluster cluster = Cluster.builder().addContactPoint("localhost").withPort(9042).withNettyOptions(new NettyOptions() {
                @Override
                public void onClusterClose(EventLoopGroup eventLoopGroup) {
                    eventLoopGroup.shutdownGracefully(0, 0, TimeUnit.MILLISECONDS).syncUninterruptibly();
                }
            }).build();

            Session session = cluster.newSession();

            try {

                session.execute("DROP KEYSPACE  test_junit ;");
            }catch(Exception e){
                log.error("KEYSPACE DELETION FAILED",e);
            }finally {
                session.close();
                cluster.close();
            }
    }
    
}
