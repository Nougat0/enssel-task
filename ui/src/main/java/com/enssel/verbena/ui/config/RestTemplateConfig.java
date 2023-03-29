package com.enssel.verbena.ui.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * RestTemplate에 대한 설정 클래스
 * 
 * @author Enssel
 */
@Configuration
public class RestTemplateConfig {
	
	String userURI = "http://localhost:8082/";
	String menuURI = "http://localhost:8083/";
	
	private String zuul = "localhost:12400/";
	
	/**
	 * RestTemplate에 대한 @Bean 설정하는 메소드
	 * 
	 * @return 설정된 템플릿을 반환
	 */
	@Bean(name="userRestTemplate")
	public RestTemplate userRestTemplate() {
		RestTemplate restTemplate = new RestTemplate();
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(userURI);
		//UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://"+zuul+apiType);
		
		restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(builder));
		
		// 허프로님께 질문 할 것
		
		return restTemplate;
	}
	
	
	@Bean(name="menuRestTemplate")
	public RestTemplate menuRestTemplate() {
		RestTemplate restTemplate = new RestTemplate();
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(menuURI);
		restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(builder));
		
		// 허프로님께 질문 할 것
		
		return restTemplate;
	}
	
}
