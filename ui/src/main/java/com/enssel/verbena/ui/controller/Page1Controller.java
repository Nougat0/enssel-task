package com.enssel.verbena.ui.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@RequestMapping("/page1")
@RestController
public class Page1Controller {

	@Autowired
	private RestTemplate restTemplate;

	@RequestMapping("/table")
	public ResponseEntity<Object> getTableRow(@RequestBody(required=false) String json) {
		if(json == null) { //검색 안 넘길 시
			Object result =  restTemplate.getForObject("/page1/table", Object.class); //O
			return new ResponseEntity<Object>( result, HttpStatus.OK);
		}
		else { //검색할 것 넘길 시 - String json 은 넘어온 검색 항목들을 포함하고 있음
			Gson gson = new Gson();
			Map<String, Object> jsonObject = gson.fromJson(json, new TypeToken<Map<String, Object>>(){}.getType());
			Object result =  restTemplate.postForObject("/page1/table", jsonObject ,Object.class); //O
			return new ResponseEntity<Object>( result, HttpStatus.OK);			
		}

	}	
	@RequestMapping("/groupBy")
	public ResponseEntity<Object> getGroupByRow(@RequestBody(required=false) String json) {
		System.out.println("검색폼json: "+json);
		if(json == null) { //검색 안 넘길 시
			Object result =  restTemplate.getForObject("/page1/groupBy", Object.class); //O
			return new ResponseEntity<Object>( result, HttpStatus.OK);
		}
		else { //검색할 것 넘길 시 - String json 은 넘어온 검색 항목들을 포함하고 있음
			Gson gson = new Gson();
			Map<String, Object> jsonObject = gson.fromJson(json, new TypeToken<Map<String, Object>>(){}.getType());
			Object result =  restTemplate.postForObject("/page1/groupBy", jsonObject ,Object.class); //O
			return new ResponseEntity<Object>( result, HttpStatus.OK);			
		}
		
		
	}	
	
	@PostMapping("/regi")
	public ResponseEntity<Object> insertMember(@RequestBody String json) throws JsonProcessingException {
		//String -> Map	
		Gson gson = new Gson();
		Map<String, Object> jsonObject = gson.fromJson(json, new TypeToken<Map<String, Object>>(){}.getType());
		Object result = restTemplate.postForObject("/page1/regi", jsonObject, Object.class);
		return new ResponseEntity<Object>(result, HttpStatus.OK);
	}	
	
	@RequestMapping(value="/update")
	public ResponseEntity<Object> updateMember(@RequestBody String json) throws JsonProcessingException {
		//String -> Map
		Gson gson = new Gson();
		Map<String, Object> jsonObject = gson.fromJson(json, new TypeToken<Map<String, Object>>(){}.getType());
		Object result = restTemplate.postForObject("/page1/update", jsonObject, String.class);
		return new ResponseEntity<Object>(result, HttpStatus.OK);
	}	
	
	@RequestMapping("/delete")
	public ResponseEntity<Object> deleteMember(@RequestParam(value="key[]") String [] keys){
		Object result = restTemplate.postForEntity("/page1/delete", keys, String.class);
		return new ResponseEntity<Object>(result, HttpStatus.OK);
	}	
	
}
