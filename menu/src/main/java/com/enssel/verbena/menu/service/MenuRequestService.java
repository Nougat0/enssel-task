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
	
	/**
	 * 회원 읽어오기
	 */
	@Autowired
	private MenuRepository menuRepository;
	
//	@Autowired
//	private QTestNougat0Menu qTestNougat0Menu;

	@Autowired
	private JPAQueryFactory jpaQueryFactory;

	@Autowired
	EntityManager entityManager;

	@Transactional
	public void deleteMenus(Integer [] keys) {

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

	public TestNougat0Menu addOneMenu(TestNougat0Menu testNougat0Menu) {
		TestNougat0Menu menu = new TestNougat0Menu();
//		menu.setMenuId("NEXT VALUE FOR test.SEQ_DATA_ID");
		menu.setMenuId(selectLastId()+1);
		menu.setMenuNm(testNougat0Menu.getMenuNm());
		menu.setSort(selectLastSort(testNougat0Menu.getSort())+1);
		
		menu.setUprMenuId(testNougat0Menu.getUprMenuId());

		//DB에는 서버 주소를 넣지 않는다.
		//다른 픅로젝트에서 요청할 경우나 서버 경로가 바뀔 경우 12400 부분을 고쳐야 하므로...
		//http://localhost:12400 은 꺼내서 쓸 때 붙이는 걸로!
		menu.setUrl("/page2/menu"+menu.getMenuId());
		menu.setUseYn("Y");
		menu.setRegiUser(testNougat0Menu.getRegiUser());
		menu.setRegiDt(LocalDateTime.now());
		return menuRepository.save(menu);
	}
	
	/**
	 * Sequence 사용하기 위한 메소드
	 * 
	 * @return 제일 큰 값(가장 마지막으로 입력된)의 menuId int
	 */
	public int selectLastId() {
		QTestNougat0Menu qTestNougat0Menu = QTestNougat0Menu.testNougat0Menu;
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
	
	
	/**
	 * 같은 부모 아래 sort를 가져오기 위한 메소드
	 * 
	 * @param uprMenuId
	 * @return
	 */
	public int selectLastSort(int uprMenuId) {
		QTestNougat0Menu qTestNougat0Menu = QTestNougat0Menu.testNougat0Menu;
		int lastSort;
		
		BooleanBuilder builder = new BooleanBuilder();
		builder.and(qTestNougat0Menu.useYn.eq("Y"));
		builder.and(qTestNougat0Menu.uprMenuId.eq(uprMenuId));
		
		try {
		lastSort = jpaQueryFactory
				.select(qTestNougat0Menu.sort)
				.from(qTestNougat0Menu)
				.where(builder)
				.orderBy(qTestNougat0Menu.sort.desc())
				.fetchFirst();
		}
		catch(NullPointerException e) {
			e.printStackTrace();
			lastSort = 1;
		}

		return lastSort;
	}
	
	/**
	 * 모달창을 활용한 메뉴 1개 수정하기
	 * 
	 * @param testNougat0Menu
	 * @return
	 */
	public TestNougat0Menu updateMenu(TestNougat0Menu testNougat0Menu) {
		TestNougat0Menu menu = menuRepository.findById(testNougat0Menu.getMenuId()).orElseGet(null);
		
		menu.setMenuNm(testNougat0Menu.getMenuNm());
		menu.setUrl(testNougat0Menu.getUrl());
		menu.setUpdaUser("ADMIN");
		menu.setUpdaDt(LocalDateTime.now());

		return menu;
	}
	
	/**
	 * 드래그 & 드롭으로 메뉴 이동 시 sort 순서 기억하기
	 * 
	 * 생각해보니까 이동한 애도 바꿔야 하지만 기존 것도 바꿔야 하네
	 * 해당 부모를 가져와서 부모 하위 애들을 전부 바꿔야 하네
	 * 
	 * @param testNougat0Menu
	 */
	public void updateMenuSort(TestNougat0Menu testNougat0Menu) {
		TestNougat0Menu menu = menuRepository.findById(testNougat0Menu.getMenuId()).orElseGet(null);
		
		menu.setSort(testNougat0Menu.getSort());
	}

}
