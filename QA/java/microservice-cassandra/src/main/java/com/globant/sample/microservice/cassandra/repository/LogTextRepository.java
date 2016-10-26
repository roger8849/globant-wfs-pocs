/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.globant.sample.microservice.cassandra.repository;

import com.globant.sample.microservice.cassandra.entity.LogReg;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Juan Krzemien
 */
public interface LogTextRepository extends CassandraRepository<LogReg> {

}
