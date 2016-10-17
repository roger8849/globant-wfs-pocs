/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.springboot.cassandra2;


import com.mycompany.springboot.cassandra2.config.*;
import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 *
 * @author sebastian.pacheco
 */
public class Main {
    
    
    public static void main(String[] args){
        
        SpringApplication.run( BasicConfiguration.class, args).close();
        
    }  

}
