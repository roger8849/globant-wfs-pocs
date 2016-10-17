package com.wfs.poc.service.impl;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wfs.poc.model.QueueMessages;
import com.wfs.poc.service.GetService;

@Service("getService")
public class DefaultGetService implements GetService {

	@Autowired
	QueueMessages queueMessages;
	
	@Override
	public String getMessages() {
		JSONArray array = new JSONArray(queueMessages.getMessagesArray());
		return array.toString(1);
	}

}
