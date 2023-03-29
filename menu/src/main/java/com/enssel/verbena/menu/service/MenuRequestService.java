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

//	public List<TestNougat0Menu> findAllMembers(){
////		List<TestNougat0> memberList = memberRepository.findAll();
//		List<TestNougat0Menu> menuList = menuRepository.findByUseYn("Y");
//		return menuList;
//	}

	public void deleteMenu(String[] keys) {
		// TODO Auto-generated method stub
		
	}

	public List<TestNougat0Menu> showMenu() {
		List<TestNougat0Menu> menuList = menuRepository.findByUseYn("Y");
		return menuList;
	}

	public List findBySearchFormGroupBy(String searchForm) {
		// TODO Auto-generated method stub
		return null;
	}

//	public TestNougat0Menu updateOneMember(TestNougat0Menu member) {
//		// TODO Auto-generated method stub
//		return null;
//	}

	public TestNougat0Menu addOneMenu(TestNougat0Menu testNougat0Menu) {
		TestNougat0Menu menu = new TestNougat0Menu();
		menu.setMenuId(testNougat0Menu.getMenuId());
		menu.setMenuNm(testNougat0Menu.getMenuNm());
		menu.setSort(0);
		
		if(testNougat0Menu.getUprMenuId() == -1) //null 과 비교가 안 되네
			menu.setUprMenuId(0);
		else 
			menu.setUprMenuId(0);
//			menu.setUprMenuId(testNougat0Menu.getUprMenuId());
		
		menu.setUrl("http://localhost:8081/page2"+"/menu"+testNougat0Menu.getMenuId());
		menu.setUseYn("Y");
		menu.setRegiUser(testNougat0Menu.getRegiUser());

//		jpaQueryFactory
//			.insert(qTestNougat0Menu)
//			.set(qTestNougat0Menu.menuId, testNougat0Menu.getMenuId());
		
		return menuRepository.save(menu);
	}

}
