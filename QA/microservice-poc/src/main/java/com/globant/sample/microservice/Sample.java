package com.globant.sample.microservice;


import javax.persistence.*;

/**
 * @author Juan Krzemien
 */
@Entity
public class Sample {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "CONTENT")
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
