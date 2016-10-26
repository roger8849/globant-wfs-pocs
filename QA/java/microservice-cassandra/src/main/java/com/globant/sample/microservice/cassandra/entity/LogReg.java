/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.globant.sample.microservice.cassandra.entity;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKey; 
import org.springframework.data.cassandra.mapping.Table;
/**
 *
 * @author sebastian.pacheco
 */
@Data
@NoArgsConstructor
@Table("LogReg")
public class LogReg {
    @PrimaryKey("id")
    private Integer id;
    @Column("logText")
    private String LogText;

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the LogText
     */
    public String getLogText() {
        return LogText;
    }

    /**
     * @param LogText the LogText to set
     */
    public void setLogText(String LogText) {
        this.LogText = LogText;
    }
    
    
    
}
