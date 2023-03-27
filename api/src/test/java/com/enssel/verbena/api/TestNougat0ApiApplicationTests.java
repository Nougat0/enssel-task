package com.enssel.verbena.api;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.enssel.verbena.api.model.TestNougat0;
import com.enssel.verbena.api.repository.MemberRepository;
import com.enssel.verbena.api.service.MemberRequestService;

@SpringBootTest
class TestNougat0ApiApplicationTests {

	@Autowired
	MemberRepository memberRepository;

	@Autowired
	MemberRequestService memberRequestService;

	@Test
	void contextLoads() {
//		List<TestNougat0> memberList = memberRepository.findByUseYn("Y");

		System.out.println(memberRequestService.findAllMembers().toString());

	}

}
