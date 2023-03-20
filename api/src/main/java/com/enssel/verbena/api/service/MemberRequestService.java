package com.enssel.verbena.api.service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enssel.verbena.api.model.TestNougat0;
import com.enssel.verbena.api.repository.MemberRepository;

@Service
public class MemberRequestService {
	
//	
//	public DataSearchResult dataSearch(DataGridParam dgp, String compCd) {
//
//        DataGridSearchService<VInspectionRequest> searchServ =  new DataGridSearchService<VInspectionRequest>(QVInspectionRequest.class, vinspRequRepo, dgp); 
//        return searchServ.getDataGridSearchResult();
//
//	}
	
	@Autowired
	private MemberRepository memberRepository;

	public List<TestNougat0> findAllMembers(){
		//Map<String,Object> map = new HashMap<>();
		//memberRepository.findAll().forEach(e->map.put("",e));
		System.out.println("🔔🔔 MemberRequestService로 들어왔습니다 🔔🔔");
//		List<TestNougat0> memberList = memberRepository.findAll();
		List<TestNougat0> memberList = memberRepository.selectAllMemberWithY();
		System.out.println("🔔🔔 findAllMembers() 함수 실행 🔔🔔");
		
		return memberList;
	}

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

		//테이블 입력
		return memberRepository.save(testNougat0);
	}
	
	public int updateOneMember(
			Map<String,Object> member
			//TestNougat0 testNougat0
			) {
		// TODO Auto-generated method stub
		
		return memberRepository.updateOneMember(
//				testNougat0.getUserId()
//				testNougat0.getUserId()
//				,testNougat0.getUserNm()
//				,testNougat0.getUpdaUser()
//				,testNougat0.getPw()
				member.get("userIdAfter").toString(),
				member.get("userNm").toString(),
				member.get("updaUser").toString(),
				member.get("pw").toString(),
				member.get("userIdBefore").toString()
				);
	}

	//int를 반환하고 싶으면 forEach 각각의 결과로 가져와진 deleteByUserYn 메소드의 결과값들을 전부 세야 함(람다함수로 안될듯)
	public void deleteMembers(String[] keys) {
		// TODO Auto-generated method stub
		Iterable<String> iterable = Arrays.asList(keys);
		iterable.forEach((userId)->memberRepository.deleteByUserYn(userId));
		System.out.println("service.java -> 회원 1~N명 delete 성공");
	}

	
}
