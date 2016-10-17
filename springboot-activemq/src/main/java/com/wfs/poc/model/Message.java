package com.wfs.poc.model;

import java.io.Serializable;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class Message implements Serializable {
	/** */
	private static final long serialVersionUID = 1L;
	
	private String message;
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
