package com.enssel.verbena.ui.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * RestTemplate에 대한 설정 클래스
 * 
 * @author 김혜민
 */
@Configuration
public class RestTemplateConfig {
	
	private String zuul = "http://localhost:12400/";
	
	@Bean
	public RestTemplate RestTemplate() {
		RestTemplate restTemplate = new RestTemplate();
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(zuul);
		restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(builder));
		return restTemplate;
	}
	
	
}
