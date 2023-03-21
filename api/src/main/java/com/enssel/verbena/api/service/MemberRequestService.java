package com.enssel.verbena.api.service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enssel.verbena.api.model.TestNougat0;
import com.enssel.verbena.api.repository.MemberRepository;

/**
 * @author Enssel
 *
 */
@Service
public class MemberRequestService {
	
//	
//	public DataSearchResult dataSearch(DataGridParam dgp, String compCd) {
//
//        DataGridSearchService<VInspectionRequest> searchServ =  new DataGridSearchService<VInspectionRequest>(QVInspectionRequest.class, vinspRequRepo, dgp); 
//        return searchServ.getDataGridSearchResult();
//
//	}
	
	
	/**
	 * 회원 읽어오기
	 */
	@Autowired
	private MemberRepository memberRepository;

	public List<TestNougat0> findAllMembers(){
//		List<TestNougat0> memberList = memberRepository.findAll();
		
		List<TestNougat0> memberList = memberRepository.findByUseYn("Y");
		
		return memberList;
	}

	
	/**
	 * 회원 한 명 등록
	 * 
	 * @param testNougat0
	 * @return testNougat0
	 */
	public TestNougat0 addOneMember(TestNougat0 testNougat0){
//		System.out.println("🔔🔔 MemberRepository로 들어왔습니다 🔔🔔");
//		System.out.println("🔔🔔 addOneMember() 함수 실행 🔔🔔");
		//Map<String,Object> map = new HashMap<>();
		//memberRepository.findAll().forEach(e->map.put("",e));
//		String timeStamp = new Timestamp(System.currentTimeMillis()).toString();
		//System.out.println(timeStamp);
		//System.out.println(new Timestamp(System.currentTimeMillis()));
		
		//TestNougat0 testNougat0 = new TestNougat0();
		
		//전달받은 값 테이블에 세팅하기
//		testNougat0.setUserNm(map.get("user_nm").toString());
//		testNougat0.setUserId(map.get("user_id").toString());
//		testNougat0.setPw(map.get("pw").toString());
//		testNougat0.setRegiUser(map.get("regi_user").toString());
		//testNougat0.setUseYn("default"); //디폴트값으로 세팅하기 위해 null 전달 (@DynamicInsert)

//		TestNougat0 user_ = memberRepository.findById(testNougat0.getUserId())/* .get() */.orElseGet(null);
//		if(user_ == null) {
//			System.out.println("동일한 pk값 없음");
//		}
//		else {
			TestNougat0 user = new TestNougat0();
			
			user.setUserNm(testNougat0.getUserNm());
			user.setPw(testNougat0.getPw());		
			user.setUserId(testNougat0.getUserId());
			user.setRegiUser("ADMIN");
			user.setRegiDt(LocalDateTime.now()); //			
//		}
		//테이블 입력
		return memberRepository.save(user);
	}
	
	
	/**
	 * 회원 한 명 수정하기
	 * 
	 * @param testNougat0
	 * @return testNougat0
	 */
	public TestNougat0 updateOneMember(TestNougat0 testNougat0) {
		
		TestNougat0 user = memberRepository.findById(testNougat0.getUserId()).orElseGet(null);
		
		user.setUserNm(testNougat0.getUserNm());
		user.setPw(testNougat0.getPw());
		user.setUpdaUser("ADMIN");
		user.setUpdaDt(LocalDateTime.now());
		
		return memberRepository.save(user);
	}

	/**
	 * 회원 1~N명 삭제하기 (Use_Yn): 'N'으로 변경하기
	 * 
	 * @param keys
	 */
	public void deleteMembers(String[] keys) {
		// TODO Auto-generated method stub
		Iterable<String> iterable = Arrays.asList(keys);
//		iterable.forEach((userId)->memberRepository.deleteByUserYn(userId));
		System.out.println("🔔API/MemberRequestService.java/deleteMembers🔔");
		iterable.forEach(key->System.out.println(key));
		
		
		List<TestNougat0> memberList = memberRepository.findAllById(iterable);
		memberList.forEach(member->member.setUseYn("N"));
		memberRepository.saveAll(memberList);
		
		System.out.println("service.java -> 회원 1~N명 delete 성공");
	}

	
}
