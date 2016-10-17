/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.springboot.cassandra2.repository;

import com.mycompany.springboot.cassandra2.entity.LogReg;
import java.io.Serializable;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author sebastian.pacheco
 */
public interface LogTextRepository extends CrudRepository<LogReg, Integer>{
    
}
