package com.enssel.verbena.menu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.enssel.verbena.menu.model.TestNougat0Menu;

@Repository
public interface MenuRepository extends JpaRepository<TestNougat0Menu, String>, QuerydslPredicateExecutor<TestNougat0Menu> {

	List<TestNougat0Menu> findByUseYn(String string);

//	List<TestNougat0Menu> findAllByMenuId(Iterable<Integer> iterable);

}
