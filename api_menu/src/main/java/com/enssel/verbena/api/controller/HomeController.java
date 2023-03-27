package com.enssel.verbena.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {
	
	@ResponseBody
	@RequestMapping("/")
	public String home() {
		return "Spring 과제 1 :: api 프로젝트입니다";
	}
	
}
