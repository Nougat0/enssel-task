package com.enssel.verbena.ui.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

	// 페이지 반환하는 컨트로러

	@RequestMapping("/")
	public String home() {
		return "index";
	}

//	@RequestMapping("/page1")
//	public String page1() {
//		return "page1";
//	}

	@GetMapping("/page1")
	public ModelAndView page() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("page1");
		return modelAndView;
	}

	// 겂을 반환하는 컨트롤러
	// Page1Controller.java 로 옮김

	
//	@RequestMapping("/page1/table")
//	@ResponseBody
//	public ResponseEntity<String> getTableRow() {
//		
//		String result =  restTemplate.getForObject("/page1/table", String.class);
//		
////		if(result.equals("실패")) {
////			return new ResponseEntity<String>(result, HttpStatus.SERVICE_UNAVAILABLE);
////		}
//		
//		return new ResponseEntity<String>(result, HttpStatus.OK);
//	}
//	
//	@PostMapping("/page1/regi")
//	@ResponseBody
//	public ResponseEntity<String> insertMember(@RequestParam Map<String,Object> member){
//		System.out.println("입력된 member 컨트롤러에서 확인: "+member);
//		System.out.println("확인중");
//		System.out.println(member.get("user_nm"));
//		System.out.println(member.get("user_id"));
//		System.out.println(member.get("regi_user"));
//		System.out.println(member.get("pw"));
//
//		//return new ResponseEntity<String>(result, HttpStatus.OK);
//		
//		return restTemplate.postForEntity("/page1/regi", member, String.class);
//	}
	
}
