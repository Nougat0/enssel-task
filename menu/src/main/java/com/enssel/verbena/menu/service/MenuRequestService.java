package com.enssel.verbena.menu.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enssel.verbena.menu.model.QTestNougat0Menu;
import com.enssel.verbena.menu.model.TestNougat0Menu;
import com.enssel.verbena.menu.repository.MenuRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

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

	@Transactional
	public void deleteMenus(Integer [] keys) {
		// TODO Auto-generated method stub
//		Iterable<Integer> iterable = Arrays.asList(keys);
//		System.out.println("🔔API/MemberRequestService.java/deleteMembers🔔");
//		iterable.forEach(key->key.toString()); 
//
//		List<TestNougat0Menu> memberList = menuRepository.findAllById(iterable);
//		memberList.forEach(member->member.setUseYn("N"));
//		menuRepository.saveAll(memberList);
		QTestNougat0Menu qTestNougat0Menu = QTestNougat0Menu.testNougat0Menu;
		
		BooleanBuilder builder = new BooleanBuilder();
		builder.and(qTestNougat0Menu.menuId.in(keys));
		
		jpaQueryFactory
			.update(qTestNougat0Menu)
			.set(qTestNougat0Menu.useYn, "N")
			.where(builder)
			.execute();

		System.out.println("service.java -> 메뉴 1~N개 delete 성공");		
	}

	public List<TestNougat0Menu> showMenu() {
		List<TestNougat0Menu> menuList = menuRepository.findByUseYn("Y");
		return menuList;
	}


//	public TestNougat0Menu updateOneMember(TestNougat0Menu member) {
//		// TODO Auto-generated method stub
//		return null;
//	}

	public TestNougat0Menu addOneMenu(TestNougat0Menu testNougat0Menu) {
		TestNougat0Menu menu = new TestNougat0Menu();
//		menu.setMenuId("NEXT VALUE FOR test.SEQ_DATA_ID");
		menu.setMenuId(selectLastId()+1);
		menu.setMenuNm(testNougat0Menu.getMenuNm());
		menu.setSort(0);
		
		menu.setUprMenuId(testNougat0Menu.getUprMenuId());

		//DB에는 서버 주소를 넣지 않는다.
		//다른 픅로젝트에서 요청할 경우나 서버 경로가 바뀔 경우 12400 부분을 고쳐야 하므로...
		//http://localhost:12400 은 꺼내서 쓸 때 붙이는 걸로!
		menu.setUrl("/page2/menu"+menu.getMenuId());
		menu.setUseYn("Y");
		menu.setRegiUser(testNougat0Menu.getRegiUser());
		menu.setRegiDt(LocalDateTime.now());

//		jpaQueryFactory
//			.insert(qTestNougat0Menu)
//			.set(qTestNougat0Menu.menuId, testNougat0Menu.getMenuId());
		
		return menuRepository.save(menu);
	}
	
	//Sequence 사용하기 위한 메소드
	public int selectLastId() {
//		BooleanBuilder builder = new BooleanBuilder();
//		builder.and(qTestNougat0Menu.useYn.eq("Y"));
		QTestNougat0Menu qTestNougat0Menu = QTestNougat0Menu.testNougat0Menu;
		TestNougat0Menu menu = new TestNougat0Menu();
		int lastId;
		try {
			lastId = jpaQueryFactory
					.select(qTestNougat0Menu.menuId)
					.from(qTestNougat0Menu)
					.where(new BooleanBuilder().and(qTestNougat0Menu.useYn.eq("Y")))
					.orderBy(qTestNougat0Menu.menuId.desc())
					.fetchFirst();
			System.out.println("MenuRequestService - selectLastId() 실행결과: "+lastId);
		}
		catch(NullPointerException e){
			e.printStackTrace();
			lastId = 0;
		}

		
		return lastId;
	}

}
