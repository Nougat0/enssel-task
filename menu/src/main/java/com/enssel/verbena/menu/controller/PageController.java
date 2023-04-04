package com.enssel.verbena.menu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.enssel.verbena.menu.model.TestNougat0Menu;
import com.enssel.verbena.menu.service.MenuRequestService;

@RestController
//@RequestMapping("/page2")
public class PageController {
	
	@Autowired
	MenuRequestService menuRequestService;
	
	@RequestMapping("/table")
	public ResponseEntity<List<TestNougat0Menu>> table() {
		System.out.println("🔔🔔menu의 컨트롤러(/table)로 들어왔습니다🔔🔔");

		//테이블 내용 조회
		List<TestNougat0Menu> menuList = menuRequestService.showMenu();
		System.out.println("검색결과 "+menuList.size()+" 개의 레코드가 전달되었습니다");
		return new ResponseEntity<List<TestNougat0Menu>>(menuList, HttpStatus.OK);
	}
	
	@RequestMapping("/regi")
	public ResponseEntity<TestNougat0Menu> regi(@RequestBody TestNougat0Menu menu) {
		System.out.println("PageController.java (/regi)로 들어왔습니다");
		TestNougat0Menu menu_ = menuRequestService.addOneMenu(menu);
		return new ResponseEntity<TestNougat0Menu>(menu_, HttpStatus.OK);
	}

	@RequestMapping("/delete")
	public void delete(@RequestBody Integer[] keys) {
		//테이블 레코드 등록
		menuRequestService.deleteMenus(keys);
	}
	
}
