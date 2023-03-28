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

	// @Autowired
	// private RestTemplateFactory restTemplateFactory;
	// @Autowired
	// private RestTemplateTest restTemplateTest;
	
	
	@Autowired
	private RestTemplate userRestTemplate;
	
//	@RequestMapping("/table")
//	public ResponseEntity<String> getTableRow() {
//		
//		String result =  restTemplate.getForObject("/page1/table", String.class);
//
//		return new ResponseEntity<String>(result, HttpStatus.OK);
//	}
	
	@RequestMapping("/table")
	public ResponseEntity<Object> getTableRow(@RequestBody(required=false) String json) {
		System.out.println("검색폼json: "+json);
		if(json == null) { //검색 안 넘길 시
			Object result =  userRestTemplate.getForObject("/page1/table", Object.class); //O
			//Gson gson = new Gson();
			//List<Map<String,Object>> jsonObject = gson.fromJson(result, new TypeToken<Object>(){}.getType());
			
			return new ResponseEntity<Object>( result, HttpStatus.OK);
		}
		else { //검색할 것 넘길 시 - String json 은 넘어온 검색 항목들을 포함하고 있음
			Gson gson = new Gson();
			Map<String, Object> jsonObject = gson.fromJson(json, new TypeToken<Map<String, Object>>(){}.getType());
			Object result =  userRestTemplate.postForObject("/page1/table", jsonObject ,Object.class); //O
			//Gson gson = new Gson();
			//List<Map<String,Object>> jsonObject = gson.fromJson(result, new TypeToken<Object>(){}.getType());
			
			return new ResponseEntity<Object>( result, HttpStatus.OK);			
		}

		
	}	
	@RequestMapping("/groupBy")
	public ResponseEntity<Object> getGroupByRow(@RequestBody(required=false) String json) {
		System.out.println("검색폼json: "+json);
		if(json == null) { //검색 안 넘길 시
			Object result =  userRestTemplate.getForObject("/page1/groupBy", Object.class); //O
			//Gson gson = new Gson();
			//List<Map<String,Object>> jsonObject = gson.fromJson(result, new TypeToken<Object>(){}.getType());
			
			return new ResponseEntity<Object>( result, HttpStatus.OK);
		}
		else { //검색할 것 넘길 시 - String json 은 넘어온 검색 항목들을 포함하고 있음
			Gson gson = new Gson();
			Map<String, Object> jsonObject = gson.fromJson(json, new TypeToken<Map<String, Object>>(){}.getType());
			Object result =  userRestTemplate.postForObject("/page1/groupBy", jsonObject ,Object.class); //O
			//Gson gson = new Gson();
			//List<Map<String,Object>> jsonObject = gson.fromJson(result, new TypeToken<Object>(){}.getType());
			
			return new ResponseEntity<Object>( result, HttpStatus.OK);			
		}
		
		
	}	
	
	@PostMapping("/regi")
	public ResponseEntity<Object> insertMember(@RequestBody String json) throws JsonProcessingException {
		//String -> Map	
		Gson gson = new Gson();
		Map<String, Object> jsonObject = gson.fromJson(json, new TypeToken<Map<String, Object>>(){}.getType());
		System.out.println("UI/page1/regi 컨트롤러에서 확인: "+jsonObject);
		
		Object result = userRestTemplate.postForObject("/page1/regi", jsonObject, Object.class);
		return new ResponseEntity<Object>(result, HttpStatus.OK);
	}	
	
	@RequestMapping(value="/update")
	public ResponseEntity<Object> updateMember(@RequestBody String json) throws JsonProcessingException {
		//String -> Map
		System.out.println("memberJSON:"+json);
		
		Gson gson = new Gson();
		//Map<String,Object> jsonObject = new HashMap<>();
		//Map<String, Object> jsonObject = gson.fromJson(json, new TypeToken<Map<String, Object>>(){}.getType());
		Map<String, Object> jsonObject = gson.fromJson(json, new TypeToken<Map<String, Object>>(){}.getType());
		//jsonObject = gson.fromJson(json, jsonObject.getClass());
		System.out.println("UI/page1/update 컨트롤러에서 확인: "+jsonObject);
		
		Object result = userRestTemplate.postForObject("/page1/update", jsonObject, String.class);
		return new ResponseEntity<Object>(result, HttpStatus.OK);
	}	
	@RequestMapping("/delete")
	public ResponseEntity<Object> deleteMember(@RequestParam(value="key[]") String [] keys){
		System.out.println("UI/page1/delete 입력된 key값 배열 컨트롤러에서 확인: "+keys);
		Object result = userRestTemplate.postForEntity("/page1/delete", keys, String.class);
		return new ResponseEntity<Object>(result, HttpStatus.OK);
	}	
	
//	@RequestMapping("/search")
//	public ResponseEntity<Object> searchMember(@RequestBody String json) {
//		Gson gson = new Gson();
//		Map<String, Object> jsonObject = gson.fromJson(json,new TypeToken<Map<String, Object>>(){}.getType());
//		System.out.println("UI/page1/update 컨트롤러에서 확인: "+jsonObject);
//		
//		Object result = restTemplate.postForObject("/page1/search", jsonObject, String.class);
//		return new ResponseEntity<Object>(result, HttpStatus.OK);
//	}
	
	
}
