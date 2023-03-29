package com.enssel.verbena.menu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {
	
	@ResponseBody
	@RequestMapping("/")
	public String home() {
		return "MSA 과제 1 :: menu 프로젝트입니다";
	}
	
}
