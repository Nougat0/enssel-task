package com.enssel.verbena.ui.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RestTemplateTest {
	private final RestTemplate restTemplate;

	@Autowired
	public RestTemplateTest(RestTemplateBuilder restTemplateBuilder) {
		this.restTemplate = restTemplateBuilder.build();
	}
	
	public String findOne(String addr) {
		return this.restTemplate.getForObject(
				"https://localhost:8081"+addr,
				String.class
		);
	}
	
	
}
