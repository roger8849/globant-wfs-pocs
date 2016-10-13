package com.wfs.poc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wfs.poc.service.GetService;

@RestController
public class ReceiveController {
	
	@Autowired
	GetService getService;
	
	@RequestMapping(value = {"/getMessages"}, method = RequestMethod.GET)
	public String getMessages(){
		return this.getService.getMessages();
	}
	
}
