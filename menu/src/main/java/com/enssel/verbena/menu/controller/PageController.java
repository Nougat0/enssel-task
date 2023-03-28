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
@RequestMapping("/page2")
public class PageController {
	
	@Autowired
	MenuRequestService menuRequestService;
	
	@RequestMapping("/table")
	public ResponseEntity<List<TestNougat0Menu>> table(@RequestBody(required=false) String searchForm) {
		System.out.println("🔔🔔api의 컨트롤러로 들어왔습니다🔔🔔");
		System.out.println("searchForm: "+searchForm);

		//테이블 내용 조회
		List<TestNougat0Menu> memberList = menuRequestService.findBySearchForm(searchForm);
		System.out.println("🔔🔔memberList 가져왔습니다🔔🔔 :: 검색조건 전달됨!"+searchForm);
		System.out.println("검색결과 "+memberList.size()+" 개의 레코드가 전달되었습니다");
		return new ResponseEntity<List<TestNougat0Menu>>(memberList, HttpStatus.OK);
	}
	@RequestMapping("/groupBy")
	public ResponseEntity<List> groupBy(@RequestBody(required=false) String searchForm) {	
		//테이블 내용 조회
		List memberList = menuRequestService.findBySearchFormGroupBy(searchForm);
		System.out.println("🔔🔔memberList 가져왔습니다🔔🔔 :: 검색조건 전달됨!"+searchForm);
		System.out.println("검색결과 "+memberList.size()+" 개의 레코드가 전달되었습니다");
		return new ResponseEntity<List>(memberList, HttpStatus.OK);
	}
	
	@RequestMapping("/regi")
	public ResponseEntity<TestNougat0Menu> regi(@RequestBody TestNougat0Menu member) {
		TestNougat0Menu user = menuRequestService.addOneMember(member);
		return new ResponseEntity<TestNougat0Menu>(user, HttpStatus.OK);
	}
	
	@RequestMapping("/update")
	public ResponseEntity<TestNougat0Menu> update(@RequestBody TestNougat0Menu member) {
		TestNougat0Menu user = menuRequestService.updateOneMember(member);
		
		return new ResponseEntity<TestNougat0Menu>(user, HttpStatus.OK);
	}
	
	@RequestMapping("/delete")
	public void delete(@RequestBody String[] keys) {
		//테이블 레코드 등록
		System.out.println("여기서 못 가져오는 거 같은데 delete:"+keys);
//		TestNougat0 testNougat0 = new TestNougat0();
//		testNougat0.setUserId(member.get("userId").toString());
//		testNougat0.setPw(member.get("pw").toString());
//		testNougat0.setUserNm(member.get("userNm").toString());
//		testNougat0.setRegiUser(member.get("regiUser").toString());
		
		menuRequestService.deleteMembers(keys);
	}
	
//	@RequestMapping("/search")
//	public ResponseEntity<TestNougat0> search
	
}
