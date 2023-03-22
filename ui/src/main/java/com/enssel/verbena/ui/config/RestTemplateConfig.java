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
	
	String resourceURI = "http://localhost:8082/";
	
	/**
	 * RestTemplate에 대한 @Bean 설정하는 메소드
	 * 
	 * @return 설정된 템플릿을 반환
	 */
	@Bean
	public RestTemplate restTemplate() {
		RestTemplate restTemplate = new RestTemplate();
		
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(resourceURI);
        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(builder));
        
        // 허프로님께 질문 할 것
        
        return restTemplate;
	}
	
}