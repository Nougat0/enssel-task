package com.enssel.verbena.ui.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RequestMapping("/page2")
@RestController
public class Page2Controller {
	
	@Autowired
	private RestTemplate menuRestTemplate;
	
	@RequestMapping("/table")
	public ResponseEntity<Object> getMenuRow(){
		Object result = menuRestTemplate.getForObject("/page2/table", Object.class);
		return new ResponseEntity<Object>( result, HttpStatus.OK);
	}
	
	
}
