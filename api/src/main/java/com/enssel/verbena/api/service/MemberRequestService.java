package com.enssel.verbena.api.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enssel.verbena.api.dto.DataSearchParams;
import com.enssel.verbena.api.dto.GroupByGridRows;
import com.enssel.verbena.api.model.QTestNougat0User;
import com.enssel.verbena.api.model.TestNougat0User;
import com.enssel.verbena.api.repository.MemberRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;

/**
 * @author Enssel
 *
 */
@Service
public class MemberRequestService {
	
	/**
	 * 회원 읽어오기
	 */
	@Autowired
	private MemberRepository memberRepository;
	
	@Autowired
	private JPAQueryFactory jpaQueryFactory;
	
	DataSearchParams dataSearchParams;
	
	@Autowired
	EntityManager entityManager;
	

	public List<TestNougat0User> findAllMembers(){
		List<TestNougat0User> memberList = memberRepository.findByUseYn("Y");
		return memberList;
	}
	
	//허 프로님께서 알려주신 방법: between 함수 사용하기
	public LocalDateTime convertFromTime(String a) {
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDateTime convert = LocalDate.parse(a, format).atStartOfDay();
		return convert;
	}
	//허 프로님께서 알려주신 방법: between 함수 사용하기
	public LocalDateTime convertToTime(String a) {
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDateTime convert = LocalDate.parse(a, format).atTime(LocalTime.MAX);
		return convert;
	}
	
	
	/**
	 * 회원 전체조회 및 검색조건에 따른 조회
	 * 
	 * @param DataSearchParams params
	 * @return
	 */
	public List<TestNougat0User> findBySearchForm(DataSearchParams params){
		BooleanBuilder builder = new BooleanBuilder();
		QTestNougat0User qTestNougat0 = QTestNougat0User.testNougat0User;
		//[1] BooleanBuilder 사용방법
		if(params != null) {
			System.out.println(params.toString());
			//검색조건이 넘어온 여부에 따라 where 조건 설정
			if(params.getUserId() != null && !params.getUserId().isEmpty()) 
				builder.and(qTestNougat0.userId.contains(params.getUserId()));
			if(params.getUserNm() != null && !params.getUserNm().isEmpty()) 
				builder.and(qTestNougat0.userNm.contains(params.getUserNm()));
			if(params.getRegiUser() != null && !params.getRegiUser().isEmpty()) 
				builder.and(qTestNougat0.regiUser.contains(params.getRegiUser()));
			
			//수정일이 없을 경우(null일 경우) 에러났었음
			//null이 들어올 때 빈 문자열로 대체하기
			if(params.getUpdaUser() != null && !params.getUpdaUser().isEmpty()) 
				builder.and(qTestNougat0.updaUser.contains(params.getUpdaUser()));
			
			boolean regiDtFromNull = params.getRegiDtFrom() == "" ? true : false;
			boolean regiDtToNull = params.getRegiDtTo() == "" ? true : false;
			boolean updaDtFromNull = params.getUpdaDtFrom() == "" ? true : false;
			boolean updaDtToNull = params.getUpdaDtTo() == "" ? true : false;
			
			// 둘 중 선택 안 한 값이 있는 경우 처음부터~ 끝까지~ 검색 가능하도록 함
			if(!regiDtFromNull || !regiDtToNull) {
				if(!regiDtFromNull && !regiDtToNull)
					builder.and(qTestNougat0.regiDt.between(convertFromTime(params.getRegiDtFrom()),convertToTime(params.getRegiDtTo())));
				else if(!regiDtFromNull && regiDtToNull)
					builder.and(qTestNougat0.regiDt.after(convertFromTime(params.getRegiDtFrom())));
				else if(regiDtFromNull && !regiDtToNull)
					builder.and(qTestNougat0.regiDt.before(convertToTime(params.getRegiDtTo())));
			}
			if(!updaDtFromNull || !updaDtToNull) {
				if(!updaDtFromNull && !updaDtToNull)
					builder.and(qTestNougat0.updaDt.between(convertFromTime(params.getUpdaDtFrom()),convertToTime(params.getUpdaDtTo())));
				else if(!updaDtFromNull && updaDtToNull)
					builder.and(qTestNougat0.updaDt.after(convertFromTime(params.getUpdaDtFrom())));
				else if(updaDtFromNull && !updaDtToNull)
					builder.and(qTestNougat0.updaDt.before(convertToTime(params.getUpdaDtTo())));				
			}
		}
		//default 조건 useYn = "Y"
		builder.and(qTestNougat0.useYn.eq("Y"));

		return jpaQueryFactory
				.selectFrom(QTestNougat0User.testNougat0User)
				.where(builder)
				.fetch();
		
		//[2] BooleanExpression 사용방법
	}
	/////////////////////////
	
	public List<GroupByGridRows> findBySearchFormGroupBy(DataSearchParams params){
		BooleanBuilder builder = new BooleanBuilder();
		QTestNougat0User qTestNougat0 = QTestNougat0User.testNougat0User;
		
		//[1] BooleanBuilder 사용방법
		if(params != null) {
			System.out.println(params.toString());
			//검색조건이 넘어온 여부에 따라 where 조건 설정
			if(params.getUserId() != null && !params.getUserId().isEmpty()) 
				builder.and(qTestNougat0.userId.contains(params.getUserId()));
			if(params.getUserNm() != null && !params.getUserNm().isEmpty()) 
				builder.and(qTestNougat0.userNm.contains(params.getUserNm()));
			if(params.getRegiUser() != null && !params.getRegiUser().isEmpty()) 
				builder.and(qTestNougat0.regiUser.contains(params.getRegiUser()));
			
			//수정일이 없을 경우(null일 경우) 에러났었음
			//null이 들어올 때 빈 문자열로 대체하기
			if(params.getUpdaUser() != null && !params.getUpdaUser().isEmpty()) 
				builder.and(qTestNougat0.updaUser.contains(params.getUpdaUser()));
			
			boolean regiDtFromNull = params.getRegiDtFrom() == "" ? true : false;
			boolean regiDtToNull = params.getRegiDtTo() == "" ? true : false;
			boolean updaDtFromNull = params.getUpdaDtFrom() == "" ? true : false;
			boolean updaDtToNull = params.getUpdaDtTo() == "" ? true : false;
			
			// 둘 중 선택 안 한 값이 있는 경우 처음부터~ 끝까지~ 검색 가능하도록 함
			if(!regiDtFromNull || !regiDtToNull) {
				if(!regiDtFromNull && !regiDtToNull)
					builder.and(qTestNougat0.regiDt.between(convertFromTime(params.getRegiDtFrom()),convertToTime(params.getRegiDtTo())));
				else if(!regiDtFromNull && regiDtToNull)
					builder.and(qTestNougat0.regiDt.after(convertFromTime(params.getRegiDtFrom())));
				else if(regiDtFromNull && !regiDtToNull)
					builder.and(qTestNougat0.regiDt.before(convertToTime(params.getRegiDtTo())));
			}
			if(!updaDtFromNull || !updaDtToNull) {
				if(!updaDtFromNull && !updaDtToNull)
					builder.and(qTestNougat0.updaDt.between(convertFromTime(params.getUpdaDtFrom()),convertToTime(params.getUpdaDtTo())));
				else if(!updaDtFromNull && updaDtToNull)
					builder.and(qTestNougat0.updaDt.after(convertFromTime(params.getUpdaDtFrom())));
				else if(updaDtFromNull && !updaDtToNull)
					builder.and(qTestNougat0.updaDt.before(convertToTime(params.getUpdaDtTo())));				
			}
		}
		//default 조건 useYn = "Y"
		builder.and(qTestNougat0.useYn.eq("Y"));

		StringTemplate dateFormat = Expressions.stringTemplate(
				"CONVERT(CHAR(10), {0}, 120)",
				qTestNougat0.regiDt
				);
		
		List<GroupByGridRows> result = jpaQueryFactory
				.select(
					Projections.fields( GroupByGridRows.class, 
						qTestNougat0.userNm.count().as("accountSum"), 
						dateFormat.as("regiDate")
					))
				.from(QTestNougat0User.testNougat0User)
				.where(builder)
				.groupBy(dateFormat)
				.fetch();
		return result;
	}

	/**
	 * 회원 한 명 등록
	 * 
	 * @param testNougat0User
	 * @return testNougat0
	 */
	public TestNougat0User addOneMember(TestNougat0User testNougat0User){
		QTestNougat0User qTestNougat0User = QTestNougat0User.testNougat0User;
		TestNougat0User user = new TestNougat0User();
			
		user.setUserNm(testNougat0User.getUserNm());
		user.setPw(testNougat0User.getPw());		
		user.setUserId(testNougat0User.getUserId());
		user.setRegiUser("ADMIN");
		user.setRegiDt(LocalDateTime.now()); //	
		
		jpaQueryFactory.insert(qTestNougat0User).set(qTestNougat0User.userId,testNougat0User.getUserId());
		//테이블 입력
		return memberRepository.save(user);
	}
	
	
	/**
	 * 회원 한 명 수정하기
	 * 
	 * @param testNougat0User
	 * @return testNougat0
	 */
	public TestNougat0User updateOneMember(TestNougat0User testNougat0User) {
		
		TestNougat0User user = memberRepository.findById(testNougat0User.getUserId()).orElseGet(null);
		
		user.setUserNm(testNougat0User.getUserNm());
		user.setPw(testNougat0User.getPw());
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
		Iterable<String> iterable = Arrays.asList(keys);
		iterable.forEach(key->System.out.println(key));
		
		List<TestNougat0User> memberList = memberRepository.findAllById(iterable);
		memberList.forEach(member->member.setUseYn("N"));
		memberRepository.saveAll(memberList);
		
		System.out.println("service.java -> 회원 1~N명 delete 성공");
	}



	
}
