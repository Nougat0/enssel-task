package com.enssel.verbena.menu.service;

import org.springframework.stereotype.Service;

@Service
public class DataGridSearchService {
//	@Autowired
//	EntityManager entityManager;
//	
//	private QTestNougat0 qTestNougat0;
//	
//
//	@Autowired
//	private JPAQueryFactory jpaQueryFactory;
//	
//	public List<TestNougat0> findBySearchForm(DataSearchParams params){
//		BooleanBuilder builder = new BooleanBuilder();
//		QTestNougat0 qTestNougat0 = QTestNougat0.testNougat0;
//		if(params != null) {
//			//검색조건이 넘어온 여부에 따라 where 조건 설정
//			if(params.getUserId() != null) 
//				builder.and(qTestNougat0.userId.like(params.getUserId()));
//			if(params.getUserNm() != null)
//				builder.and(qTestNougat0.userNm.like(params.getUserNm()));
//			if(params.getRegiUser() != null)
//				builder.and(qTestNougat0.regiUser.like(params.getRegiUser()));
//			if(params.getUpdaUser() != null)
//				builder.and(qTestNougat0.updaUser.like(params.getUpdaUser()));
//			
//			//이게 nullable로 에러가 안 나는지 확인해봐야 함
//			if(params.getRegiDtFrom() != null || params.getRegiDtTo() != null)
//				builder.and(qTestNougat0.regiDt.between(params.getRegiDtFrom(),params.getRegiDtTo()));
//			if(params.getUpdaDtFrom() != null || params.getUpdaDtTo() != null)
//				builder.and(qTestNougat0.updaDt.between(params.getUpdaDtFrom(),params.getUpdaDtTo()));
//			
//		}
//		//default 조건 useYn = "Y"
//		builder.and(qTestNougat0.useYn.eq("Y"));
//		
//		
//		return jpaQueryFactory
//				.selectFrom(QTestNougat0.testNougat0)
//				.where(builder)
//				.fetch();
//	}
	
}
