package com.enssel.verbena.menu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enssel.verbena.menu.model.QTestNougat0Menu;
import com.enssel.verbena.menu.model.TestNougat0Menu;
import com.enssel.verbena.menu.repository.MenuRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;

/**
 * @author Enssel
 *
 */
@Service
public class MenuRequestService {
	
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
	private MenuRepository menuRepository;
	
//	@Autowired
	private QTestNougat0Menu qTestNougat0Menu;

	@Autowired
	private JPAQueryFactory jpaQueryFactory;

	@Autowired
	EntityManager entityManager;
	
	//public final BooleanExpression operation = Expressions.booleanOperation(Ops.BETWEEN, dataSearchParams.);

	public List<TestNougat0Menu> findAllMembers(){
//		List<TestNougat0> memberList = memberRepository.findAll();
		List<TestNougat0Menu> menuList = menuRepository.findByUseYn("Y");
		return menuList;
	}

	public void deleteMembers(String[] keys) {
		// TODO Auto-generated method stub
		
	}

	public List<TestNougat0Menu> findBySearchForm(String searchForm) {
		// TODO Auto-generated method stub
		return null;
	}

	public List findBySearchFormGroupBy(String searchForm) {
		// TODO Auto-generated method stub
		return null;
	}

	public TestNougat0Menu updateOneMember(TestNougat0Menu member) {
		// TODO Auto-generated method stub
		return null;
	}

	public TestNougat0Menu addOneMember(TestNougat0Menu member) {
		// TODO Auto-generated method stub
		return null;
	}

}
