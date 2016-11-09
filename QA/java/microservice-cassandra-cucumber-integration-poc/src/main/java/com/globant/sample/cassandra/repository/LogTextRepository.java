/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.globant.sample.cassandra.repository;

import com.globant.sample.cassandra.entity.LogReg;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Juan Krzemien
 */
public interface LogTextRepository extends CrudRepository<LogReg, Integer> {

}
