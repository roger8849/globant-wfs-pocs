package com.wfs.poc.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class QueueMessages {
	private List<String> messagesArray;
	
	public QueueMessages() {
		messagesArray = new ArrayList<>();
	}

	public List<String> getMessagesArray() {
		return messagesArray;
	}

	public void setMessagesArray(List<String> messagesArray) {
		this.messagesArray = messagesArray;
	}
	
	public void addMessagesToArray(String message){
		this.messagesArray.add(message);
	}
	
	
}
