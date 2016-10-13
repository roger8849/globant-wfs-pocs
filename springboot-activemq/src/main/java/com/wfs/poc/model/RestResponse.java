package com.wfs.poc.model;

import java.io.Serializable;

public class RestResponse implements Serializable {
	/** */
	private static final long serialVersionUID = -475571295215560258L;

	private String status;
	private String message;
	private String eMessage;
	
	public RestResponse() {
		super();
	}
	
	public RestResponse(String status, String message, String eMessage) {
		this.status = status;
		this.message = message;
		this.eMessage = eMessage;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String geteMessage() {
		return eMessage;
	}
	public void seteMessage(String eMessage) {
		this.eMessage = eMessage;
	}
	
}
