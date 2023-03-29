package com.enssel.verbena.ui.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@RestController
@RequestMapping("/page2")
public class Page2Controller {
	
	@Autowired
	@Qualifier("menuRestTemplate")
	private RestTemplate menuRestTemplate;
	
	@RequestMapping("/table")
	public ResponseEntity<Object> getMenuRow(){
		Object result = menuRestTemplate.getForObject("/page2/table", Object.class);
		return new ResponseEntity<Object>( result, HttpStatus.OK);
	}
	@RequestMapping("/regi")
	public ResponseEntity<Object> addMenuRow(@RequestBody String json){
		System.out.println("page2Controller.java로 들어왔습니다");
		
		Gson gson = new Gson();
		Map<String, Object> jsonObject = gson.fromJson(json, new TypeToken<Map<String, Object>>(){}.getType());
		Object result = menuRestTemplate.postForObject("/page2/regi", jsonObject, Object.class);
		return new ResponseEntity<Object>( result, HttpStatus.OK);
	}
	
	
}
