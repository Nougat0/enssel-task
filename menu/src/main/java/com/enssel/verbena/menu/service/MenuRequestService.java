package com.enssel.verbena.menu.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.enssel.verbena.menu.dto.IdList;
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
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private MenuRepository menuRepository;

	@Autowired
	private JPAQueryFactory jpaQueryFactory;

	@Autowired
	EntityManager entityManager;
	
	//디폴트 0으로 설정해두기 (사용완료시 0으로 설정)
	private int seqNum;

	@Transactional
	public List<TestNougat0Menu> showMenu() {
		QTestNougat0Menu qTestNougat0Menu = QTestNougat0Menu.testNougat0Menu;
		List<TestNougat0Menu> menuList = jpaQueryFactory
			.selectFrom(qTestNougat0Menu)
			/*.groupBy(qTestNougat0Menu.regiUser)*/
			.fetch();
		
		Map<String, String> first = new HashMap<>();
		
		for(TestNougat0Menu a: menuList) {
			first.put(a.getRegiUser(), null);
			first.put(a.getUpdaUser(), null);
		}
		
		List<IdList> idData = first.keySet().stream().map(item->{
			return new IdList(item, null);
		}).toList();
		
		
		
		menuList.forEach(menu->{
//			menu.setRegiUser(getUserNmFromUserId(menu.getRegiUser()));
//			if(menu.getUpdaUser() != null)
//				menu.setUpdaUser(getUserNmFromUserId(menu.getUpdaUser()));
			
			String regiUserNm2 = null;
			
			for(IdList a: idData) {
				if(a.getId().equals(menu.getRegiUser())) {
					regiUserNm2 = a.getName();
					break;
				}
			}
			
			String regiUserNm = idData.stream()
					.filter(item->
						menu.getRegiUser().equals(item.getId())
					).findFirst().orElse(new IdList()).getName();
			
			
		});
		return menuList;
	}
	
	
	/**
	 * 이런 식이면 100,000줄이 있으면 전부 다 바꿔야 하는데... 너무 오래걸리지 않을까 rest 요청이
	 * 그냥 rest요청 한번으로 list 전부 가져와서 대조해서 바꾸는 건 안되나?
	 * 
	 * @param regiUser
	 * @return
	 */
	@Transactional
	public String getUserNmFromUserId(String regiUser) {
		return restTemplate.postForObject("/page1/getNm", regiUser, String.class);
	}
	
	
	@Transactional
	public TestNougat0Menu addOneMenu(TestNougat0Menu testNougat0Menu) {
		TestNougat0Menu menu = new TestNougat0Menu();
//		menu.setMenuId("NEXT VALUE FOR test.SEQ_DATA_ID");
		menu.setMenuId(selectLastId()+1);
		menu.setMenuNm(testNougat0Menu.getMenuNm());
		menu.setSort(selectLastSort(testNougat0Menu.getUprMenuId())+1);
		
		menu.setUprMenuId(testNougat0Menu.getUprMenuId());

		//DB에는 서버 주소를 넣지 않는다.
		//다른 픅로젝트에서 요청할 경우나 서버 경로가 바뀔 경우 12400 부분을 고쳐야 하므로...
		//http://localhost:12400 은 꺼내서 쓸 때 붙이는 걸로!
		if(testNougat0Menu.getUrl() == null || testNougat0Menu.getUrl().isEmpty())
			menu.setUrl("/page2/menu"+menu.getMenuId());
		else 
			menu.setUrl(testNougat0Menu.getUrl());
		menu.setUseYn("Y");
		menu.setRegiUser(testNougat0Menu.getRegiUser());
		menu.setRegiDt(LocalDateTime.now());
		return menuRepository.save(menu);
	}
	
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
	
	/**
	 * Sequence 사용하기 위한 메소드
	 * 
	 * @return 제일 큰 값(가장 마지막으로 입력된)의 menuId int
	 */
	@Transactional
	public int selectLastId() {
		QTestNougat0Menu qTestNougat0Menu = QTestNougat0Menu.testNougat0Menu;
		int lastId;
		try {
			lastId = jpaQueryFactory
					.select(qTestNougat0Menu.menuId)
					.from(qTestNougat0Menu)
					//.where(new BooleanBuilder().and(qTestNougat0Menu.useYn.eq("Y")))
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
	@Transactional
	public int selectLastSort(int uprMenuId) {

		QTestNougat0Menu qTestNougat0Menu = QTestNougat0Menu.testNougat0Menu;
		int lastSort;
		
		BooleanBuilder builder = new BooleanBuilder();
		//builder.and(qTestNougat0Menu.useYn.eq("Y"));
		builder.and(qTestNougat0Menu.uprMenuId.eq(uprMenuId));
		
//		허 프로님 코드
//		Integer lastSort = jpaQueryFactory.select(
//				qTestNougat0Menu.sort.max().coalesce(0)
//			).from(qTestNougat0Menu).where(builder).fetchFirst()+1;
		
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
			lastSort = 0;
		}

		return lastSort;
	}
	
	@Transactional
	public void sort(TestNougat0Menu targetMenu, TestNougat0Menu sourceMenu) {
		updateMenu(targetMenu);
		updateTargetUprMenuSort(targetMenu);
		updateSourceUprMenuSort(sourceMenu.getUprMenuId());
	}
	
	/**
	 * 모달창을 활용한 메뉴 1개 menuNm, url 수정하기,
	 * drag&drop으로 sort 된 경우 uprMenuId, sort 수정하기 
	 * 
	 * @param testNougat0Menu
	 * @return
	 */
	@Transactional
	public TestNougat0Menu updateMenu(TestNougat0Menu testNougat0Menu) {
		System.out.println("sort1번");
		TestNougat0Menu menu = menuRepository.findById(testNougat0Menu.getMenuId()).orElseGet(null);
		//form 을 통해서 전달된 경우 아래 menuNm, url만 존재
		menu.setMenuNm(testNougat0Menu.getMenuNm());
		menu.setUrl(testNougat0Menu.getUrl());
		menu.setUpdaUser("ADMIN");
		menu.setUpdaDt(LocalDateTime.now());

		//sort 를 통해서 전달되었을 경우 아래 항목 존재
		if(testNougat0Menu.getUprMenuId() != null) 
			menu.setUprMenuId(testNougat0Menu.getUprMenuId());
		if(testNougat0Menu.getSort() != null)
			menu.setSort(testNougat0Menu.getSort());
		
		menuRepository.save(menu);
		
		return menu;
	}
	
	/**
	 * 드래그 & 드롭으로 메뉴 이동 시 sort 순서 기억하기
	 * 
	 * 생각해보니까 이동한 애도 바꿔야 하지만 기존 것도 바꿔야 하네
	 * 해당 부모를 가져와서 부모 하위 애들을 전부 바꿔야 하네
	 * 
	 * @param 순서 이동한 testNougat0Menu
	 */
	@Transactional
	public void updateTargetUprMenuSort(TestNougat0Menu testNougat0Menu) {
		System.out.println("sort2번");
		QTestNougat0Menu qTestNougat0Menu = QTestNougat0Menu.testNougat0Menu;
		BooleanBuilder builder = new BooleanBuilder();

		seqNum = testNougat0Menu.getSort();
		
		builder.and(qTestNougat0Menu.uprMenuId.eq(testNougat0Menu.getUprMenuId()));
		builder.and(qTestNougat0Menu.sort.goe(seqNum));
		builder.and(qTestNougat0Menu.menuId.notIn(testNougat0Menu.getMenuId()));
		
		List<TestNougat0Menu> list = new ArrayList<>();
		
		jpaQueryFactory
			.selectFrom(qTestNougat0Menu)
			.where(builder)
			.orderBy(qTestNougat0Menu.sort.asc())
			.fetch()
			.forEach(menu->{
				seqNum+=1;
				menu.setSort(seqNum);
				list.add(menu);
				//menuRepository.save(menu);
//				jpaQueryFactory
//					.update(qTestNougat0Menu)
//					.set(qTestNougat0Menu.sort, seqNum)
//					.where(qTestNougat0Menu.menuId.eq(menu.getMenuId()))
//					.execute();
			});
		
		menuRepository.saveAll(list);

		seqNum = 0;
	}
	
	/**
	 * 메뉴가 기존에 있던 자리의 같은 uprMenuId를 가진 형제들의 sort 다시 설정
	 * 
	 * @param uprMenuId
	 */
	@Transactional	
	public void updateSourceUprMenuSort(int uprMenuId) {
		System.out.println("sort3번");
		QTestNougat0Menu qTestNougat0Menu = QTestNougat0Menu.testNougat0Menu;
		BooleanBuilder builder = new BooleanBuilder();
		builder.and(qTestNougat0Menu.uprMenuId.eq(uprMenuId));
		seqNum = 0;
		
		jpaQueryFactory
			.selectFrom(qTestNougat0Menu)
			.where(builder)
			.orderBy(qTestNougat0Menu.sort.asc())
			.fetch()
			.forEach(menu->{
				seqNum+=1;
				menu.setSort(seqNum);
				menuRepository.save(menu);
//				jpaQueryFactory
//					.update(qTestNougat0Menu)
//					.set(qTestNougat0Menu.sort, seqNum)
//					.where(qTestNougat0Menu.menuId.eq(menu.getMenuId()))
//					.execute();
			});
		
		
	}
	
	
	


}
