package com.enssel.verbena.ui.service;

import java.net.URI;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class RestTemplateService {
	
	public ResponseEntity<Object> get() {
        URI uri = UriComponentsBuilder
                .fromUriString("http://localhost:12400")
                .path("/api/v1/crud-api")
                .encode()
                .build()
                .toUri();

        RestTemplate restTemplate = new RestTemplate();
        Object result = restTemplate.getForObject(uri, Object.class);
        return new ResponseEntity<Object>(result, HttpStatus.OK);
	}
	public ResponseEntity<Object> post(Object object) {
		URI uri = UriComponentsBuilder
				.fromUriString("http://localhost:12400")
				.path("/api/v1/crud-api")
				.encode()
				.build()
				.toUri();
		
		RestTemplate restTemplate = new RestTemplate();
		Object result = restTemplate.postForObject(uri, object, Object.class);
		return new ResponseEntity<Object>(result, HttpStatus.OK);
	}
	public ResponseEntity<Object> put() {
		URI uri = UriComponentsBuilder
				.fromUriString("http://localhost:12400")
				.path("/api/v1/crud-api")
				.encode()
				.build()
				.toUri();
		
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.put(uri, Object.class);
		return new ResponseEntity<Object>(null, HttpStatus.OK);
	}
	public ResponseEntity<Object> delete(Map<String, Object> map) {
		String uri = UriComponentsBuilder
				.fromUriString("http://localhost:12400")
				.path("/api/v1/crud-api")
				.encode()
				.build()
				.toUri()
				.toString();
		
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.delete(uri, map);
		return new ResponseEntity<Object>(null, HttpStatus.OK);
	}
}
