package com.wfs.poc.service.impl;

import javax.jms.ConnectionFactory;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import com.wfs.poc.messaging.MessageSender;
import com.wfs.poc.model.Message;
import com.wfs.poc.model.RestResponse;
import com.wfs.poc.service.SendService;


@Service("sendService")
public class DefaultSendService implements SendService {
	
	@Autowired
	MessageSender messageSender;
	@Autowired
	JmsTemplate jmsTemplate;
	@Autowired
	ConnectionFactory connectionFactory;

	private static final String TEST_QUEUE = "test-queue";
	
	@Override
	public String sendMessageToQueue(String messageString) {
		Message message = new Message();
		message.setMessage(messageString);
		RestResponse jsonResponse = null;
		
		JmsTemplate template = new JmsTemplate();
		template.setConnectionFactory(connectionFactory);
		template.setDefaultDestinationName(TEST_QUEUE);
		
		try {
			template.convertAndSend(message);
			jsonResponse = new RestResponse("200", "ok", null);
		} catch (Exception e) {
			jsonResponse = new RestResponse("500", "", "We're doomed: " + e.getMessage());
		}
		
		
		JSONObject object = new JSONObject(jsonResponse);
		
		return object.toString(1);
	}

}
