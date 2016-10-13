package com.wfs.poc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wfs.poc.service.SendService;

@RestController
public class SendController {
	
	@Autowired
	SendService sendService;
	
	@RequestMapping(value = {"/","/sendMessage"}, method = RequestMethod.GET)
	public String sendMessageToQueue(@RequestParam String message){
		return this.sendService.sendMessageToQueue(message);
	}
}
