package com.enssel.verbena.ui.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriComponentsBuilder;

@Controller
public class HomeController {

	// 페이지 반환하는 컨트로러

	@RequestMapping("/")
	public String home() {
		return "index";
	}

	@GetMapping("/page1")
	public ModelAndView page1() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("page1");
		return modelAndView;
	}
	@GetMapping("/page2")
	public ModelAndView page2() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("page2");
		return modelAndView;
	}
	
	//actuator/route 로 나오는 현재 가용한 모든 서버(서비스) 목록...
	@GetMapping("/route")
	@ResponseBody
	public ResponseEntity<Object> getRoutes(){
		RestTemplate restTemplate = new RestTemplate();
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://localhost:12400");
		restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(builder));
		Object result = restTemplate.getForObject("/actuator/routes", Object.class);
		return new ResponseEntity<Object>(result, HttpStatus.OK);
	}

	
}
