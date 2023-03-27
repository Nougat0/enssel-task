package com.enssel.verbena.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.enssel.verbena.api.dto.DataSearchParams;
import com.enssel.verbena.api.dto.GroupByGridRows;
import com.enssel.verbena.api.model.TestNougat0;
import com.enssel.verbena.api.service.DataGridSearchService;
import com.enssel.verbena.api.service.MemberRequestService;

@RestController
@RequestMapping("/page1")
public class PageController {
	
	@Autowired
	MemberRequestService memberRequestService;
	
	@Autowired
	DataGridSearchService dataGridSearchService;
	
	@RequestMapping("/table")
	public ResponseEntity<List<TestNougat0>> table(@RequestBody(required=false) DataSearchParams searchForm) {
		System.out.println("🔔🔔api의 컨트롤러로 들어왔습니다🔔🔔");
		System.out.println("searchForm: "+searchForm);
		
		
//		if(searchForm == null) {
//			//테이블 내용 조회
//			List<TestNougat0> memberList = memberRequestService.findAllMembers();
//			System.out.println("🔔🔔memberList 가져왔습니다🔔🔔 :: 검색조건 전달되지 않음");
//			return new ResponseEntity<List<TestNougat0>>(memberList, HttpStatus.OK);
////		return "실패";
////		return "🔔 api 프로젝트의 controller로 REST API 요청 성공 🔔";			
//		}
//		else {
			
			
			//테이블 내용 조회
			List<TestNougat0> memberList = memberRequestService.findBySearchForm(searchForm);
			System.out.println("🔔🔔memberList 가져왔습니다🔔🔔 :: 검색조건 전달됨!"+searchForm);
			System.out.println("검색결과 "+memberList.size()+" 개의 레코드가 전달되었습니다");
			return new ResponseEntity<List<TestNougat0>>(memberList, HttpStatus.OK);
			
			
		//}
	}
	@RequestMapping("/groupBy")
	public ResponseEntity<List<GroupByGridRows>> groupBy(@RequestBody(required=false) DataSearchParams searchForm) {	
		//테이블 내용 조회
		List<GroupByGridRows> memberList = memberRequestService.findBySearchFormGroupBy(searchForm);
		System.out.println("🔔🔔memberList 가져왔습니다🔔🔔 :: 검색조건 전달됨!"+searchForm);
		System.out.println("검색결과 "+memberList.size()+" 개의 레코드가 전달되었습니다");
		return new ResponseEntity<List<GroupByGridRows>>(memberList, HttpStatus.OK);
	}
	
	@RequestMapping("/regi")
	public ResponseEntity<TestNougat0> regi(@RequestBody TestNougat0 member) {
		TestNougat0 user = memberRequestService.addOneMember(member);
		return new ResponseEntity<TestNougat0>(user, HttpStatus.OK);
	}
	
	@RequestMapping("/update")
	public ResponseEntity<TestNougat0> update(@RequestBody TestNougat0 member) {
		TestNougat0 user = memberRequestService.updateOneMember(member);
		
		return new ResponseEntity<TestNougat0>(user, HttpStatus.OK);
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
		
		memberRequestService.deleteMembers(keys);
	}
	
//	@RequestMapping("/search")
//	public ResponseEntity<TestNougat0> search
	
}