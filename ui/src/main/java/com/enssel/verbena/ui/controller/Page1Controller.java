package com.enssel.verbena.ui.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParser;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

@RequestMapping("/page1")
@RestController
public class Page1Controller {

	// @Autowired
	// private RestTemplateFactory restTemplateFactory;
	// @Autowired
	// private RestTemplateTest restTemplateTest;
	
	
	@Autowired
	private RestTemplate restTemplate;
	
//	@RequestMapping("/table")
//	public ResponseEntity<String> getTableRow() {
//		
//		String result =  restTemplate.getForObject("/page1/table", String.class);
//
//		return new ResponseEntity<String>(result, HttpStatus.OK);
//	}
	
	@RequestMapping("/table")
	public ResponseEntity<Object> getTableRow() {
		

		
		Object result =  restTemplate.getForObject("/page1/table", Object.class); //O
		//Gson gson = new Gson();
		//List<Map<String,Object>> jsonObject = gson.fromJson(result, new TypeToken<Object>(){}.getType());
		
		return new ResponseEntity<Object>( result, HttpStatus.OK);
	}	
	
	@RequestMapping("/regi")
	public void insertMember(@RequestParam Map<String,Object> member){
		System.out.println("UI/page1/regi 컨트롤러에서 확인: "+member);
		System.out.println("확인중");
		System.out.println(member.get("userNm"));
		System.out.println(member.get("userId"));
		System.out.println(member.get("regiUser"));
		System.out.println(member.get("pw"));
		System.out.println("확인끝");
		restTemplate.postForEntity("/page1/regi", member, String.class);
	}	
	@RequestMapping("/update")
	public void updateMember(@RequestParam Map<String,Object> member){
		System.out.println("UI/page1/update 컨트롤러에서 확인: "+member);
		System.out.println("확인중");
		System.out.println(member.get("userNm"));
		System.out.println(member.get("userIdAfter"));
		System.out.println(member.get("userIdBefore"));
		System.out.println(member.get("updaUser"));
		System.out.println(member.get("pw"));
		System.out.println("확인끝");
		restTemplate.postForEntity("/page1/update", member, String.class);
	}	
	@RequestMapping("/delete")
	public void deleteMember(@RequestParam(value="key[]") String[] keys){
		System.out.println("UI/page1/delete 입력된 key값 배열 컨트롤러에서 확인: "+keys);
		System.out.println("확인중");
		System.out.println(keys);
		System.out.println("확인끝");
		restTemplate.postForEntity("/page1/delete", keys, String.class);
	}	

	
	
}
