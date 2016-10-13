package com.wfs.poc.messaging;

import javax.jms.JMSException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.wfs.poc.model.Message;
import com.wfs.poc.model.QueueMessages;


@Component
public class MessageReceiver {
	static final Logger LOG = LoggerFactory.getLogger(MessageReceiver.class);

	private static final String TEST_QUEUE = "test-queue";
	
	@Autowired
	QueueMessages queueMessages;
	
	@JmsListener(destination = TEST_QUEUE)
	public void receiveMessage(final Message message) throws JMSException {
		queueMessages.addMessagesToArray(message.getMessage());
		LOG.info("Logged message:  " + message.getMessage());
	}
}