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
import com.enssel.verbena.api.model.QTestNougat0;
import com.enssel.verbena.api.model.TestNougat0;
import com.enssel.verbena.api.repository.MemberRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.ComparableExpression;
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
	
//	@Autowired
	private QTestNougat0 qTestNougat0;

	@Autowired
	private JPAQueryFactory jpaQueryFactory;
	
	DataSearchParams dataSearchParams;
	
//	@Autowired
//	GroupByGridRows groupByGridRows;
	
	@Autowired
	EntityManager entityManager;
	
	//public final BooleanExpression operation = Expressions.booleanOperation(Ops.BETWEEN, dataSearchParams.);

	public List<TestNougat0> findAllMembers(){
//		List<TestNougat0> memberList = memberRepository.findAll();
		List<TestNougat0> memberList = memberRepository.findByUseYn("Y");
		return memberList;
	}
	
	
	//허 프로님께서 알려주신 방법: between 함수 사용하기
	public LocalDateTime convertFromTime(String a) {
//		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//		LocalDateTime convert = LocalDate.parse(a, format).atStartOfDay();
//		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.sss");
//		LocalDateTime convert = LocalDate.parse(a, format).atStartOfDay();
		System.out.println("params로 전달된 string:"+a);
		
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDateTime convert = LocalDate.parse(a, format).atStartOfDay();

		System.out.println("⛔⛔"+convert.toString());
		
		
		return convert;
	}
	//허 프로님께서 알려주신 방법: between 함수 사용하기
	public LocalDateTime convertToTime(String a) {
		System.out.println("params로 전달된 string:"+a);
		
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDateTime convert = LocalDate.parse(a, format).atTime(LocalTime.MAX);
		
		System.out.println("⛔⛔"+convert.toString());
		
		
		return convert;
	}
	
	
	/**
	 * 회원 전체조회 및 검색조건에 따른 조회
	 * 
	 * @param DataSearchParams params
	 * @return
	 */
	public List<TestNougat0> findBySearchForm(DataSearchParams params){
		BooleanBuilder builder = new BooleanBuilder();
		QTestNougat0 qTestNougat0 = QTestNougat0.testNougat0;
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
			//
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
				.selectFrom(QTestNougat0.testNougat0)
				.where(builder)
				.fetch();
		
		//[2] BooleanExpression 사용방법
		
//		if(params != null) {
//			return jpaQueryFactory
//					.selectFrom(qTestNougat0.testNougat0)
//					.where(
//							userIdEq(params.getUserId()),
//							userNmEq(params.getUserNm()),
//							regiUserEq(params.getRegiUser()),
//							updaUserEq(params.getUpdaUser()),
//							
//							regiDtBetween(params.getRegiDtFrom(), params.getRegiDtTo()),
//							//regiDtFromEq(params.getRegiDtFrom()),
//							//regiDtToEq(params.getRegiDtTo()),
//							
//							updaDtBetween(params.getRegiDtFrom(), params.getRegiDtTo())
//							//updaDtFromEq(params.getUpdaDtFrom()),
//							//updaDtToEq(params.getUpdaDtTo())
//							)
//					.fetch();
//		}
//		else {
//			return jpaQueryFactory
//					.selectFrom(qTestNougat0.testNougat0)
//					.where(
//						useYnEq("Y")
//					)
//					.fetch();
//		}
			
	}
	/////////////////////////
	
	public List<GroupByGridRows> findBySearchFormGroupBy(DataSearchParams params){
		BooleanBuilder builder = new BooleanBuilder();
		QTestNougat0 qTestNougat0 = QTestNougat0.testNougat0;
		
//		ComparableExpression<String> dateFormatRegiDt = Expressions.stringTemplate(
//				"CONVERT(CHAR(10), {0}, 120)",
//				qTestNougat0.regiDt
//				);
//		StringTemplate dateFormatUpdaDt = Expressions.stringTemplate(
//				"CONVERT(CHAR(10), {0}, 120)",
//				qTestNougat0.updaDt
//				);
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
			//
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
		
//		NumberExpression<Long> dateCount = Expressions.template(
//				String.class, 
//				"SUBSTRING({0}, 1, CHARINDEX('T',{0}))",
//				qTestNougat0.regiDt.toString()
//			).count();
//		NumberExpression<Long> dateCount_ = Expressions.stringTemplate(
//				"CONVERT(CHAR(10), {0}, 120)",
//				qTestNougat0.regiDt
//			).count();
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
				.from(QTestNougat0.testNougat0)
				.where(builder)
				.groupBy(dateFormat)
				.fetch();
		return result;
	}
	

//	public void 동적쿼리_WhereParam() throws Exception { String usernameParam = "member1";
//	Integer ageParam = 10;
//	      List<Member> result = searchMember2(usernameParam, ageParam);
//	      Assertions.assertThat(result.size()).isEqualTo(1);
//	  }
//	  private List<Member> searchMember2(String usernameCond, Integer ageCond) {
//	      return queryFactory
//	              .selectFrom(member)
//	              .where(usernameEq(usernameCond), ageEq(ageCond))
//	              .fetch();
//	  }
//	  private BooleanExpression usernameEq(String usernameCond) {
//	      return usernameCond != null ? member.username.eq(usernameCond) : null;
//	  }
//	  private BooleanExpression ageEq(Integer ageCond) {
//	      return ageCond != null ? member.age.eq(ageCond) : null;
//	}
	/////////////////////////	  
	  
	/**
	 * useYn = "Y" 인 항목만 표시하는 query
	 */
	private Predicate useYnEq(String string) {
		// TODO Auto-generated method stub
		QTestNougat0 qTestNougat0 = QTestNougat0.testNougat0;
		return qTestNougat0.useYn.eq(string);
	}
//
//
//	private BooleanExpression updaDtToEq(LocalDateTime updaDtTo) {
//		QTestNougat0 qTestNougat0 = QTestNougat0.testNougat0;
//		return updaDtTo != null ? qTestNougat0.updaDt.eq(updaDtTo) : null;
//	}
//
//	private BooleanExpression updaDtFromEq(LocalDateTime updaDtFrom) {
//		QTestNougat0 qTestNougat0 = QTestNougat0.testNougat0;
//		return updaDtFrom != null ? qTestNougat0.updaDt.eq(updaDtFrom) : null;
//	}
//
//	private BooleanExpression regiDtToEq(LocalDateTime regiDtTo) {
//		QTestNougat0 qTestNougat0 = QTestNougat0.testNougat0;
//		return regiDtTo != null ? qTestNougat0.regiDt.eq(regiDtTo) : null;
//	}
//
//	private BooleanExpression regiDtFromEq(LocalDateTime regiDtFrom) {
//		QTestNougat0 qTestNougat0 = QTestNougat0.testNougat0;
//		return regiDtFrom != null ? qTestNougat0.regiDt.eq(regiDtFrom) : null;
//	}
//	
//	
//
//	private BooleanExpression updaDtBetween(LocalDateTime updaDtFrom, LocalDateTime updaDtTo) {
//		QTestNougat0 qTestNougat0 = QTestNougat0.testNougat0;
//		return updaDtFrom != null && updaDtTo != null ? qTestNougat0.updaDt.between(updaDtFrom, updaDtTo) : null;
//	}
//
//	private BooleanExpression regiDtBetween(LocalDateTime regiDtFrom, LocalDateTime regiDtTo) {
////		/QTestNougat0 qTestNougat0 = QTestNougat0.testNougat0;
////		if(regiDtFrom != null && regiDtTo != null) {
////			return qTestNougat0.updaDt.between(regiDtFrom, regiDtTo);
////		}
////		else if(regiDtFrom != null && regiDtTo == null) {
////			
////		}
////		
//		return regiDtFrom != null && regiDtTo != null ? qTestNougat0.updaDt.between(regiDtFrom, regiDtTo) : null;
//	}
//
//	private BooleanExpression updaUserEq(String updaUser) {
//		QTestNougat0 qTestNougat0 = QTestNougat0.testNougat0;
//		return updaUser != null ? qTestNougat0.updaUser.eq(updaUser) : null;
//	}
//
//	private BooleanExpression regiUserEq(String regiUser) {
//		QTestNougat0 qTestNougat0 = QTestNougat0.testNougat0;
//		return regiUser != null ? qTestNougat0.regiUser.eq(regiUser) : null;
//	}
//
//	private BooleanExpression userNmEq(String userNm) {
//		QTestNougat0 qTestNougat0 = QTestNougat0.testNougat0;
//		return userNm != null ? qTestNougat0.userNm.eq(userNm) : null;
//	}
//
//	private BooleanExpression userIdEq(String userId) {
//		QTestNougat0 qTestNougat0 = QTestNougat0.testNougat0;
//		return userId != null ? qTestNougat0.userId.eq(userId) : null;
//	}

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
			
			jpaQueryFactory.insert(qTestNougat0).set(qTestNougat0.userId,testNougat0.getUserId());
			
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
